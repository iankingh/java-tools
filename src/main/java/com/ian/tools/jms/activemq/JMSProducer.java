package com.ian.tools.jms.activemq;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的生產者（發送者）
 * 
 * http://localhost:8161/admin/ admin admin
 */

public class JMSProducer {

	// 默認連接用戶名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默認連接密碼
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默認連接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	// 發送的消息數量
	private static final int SENDNUM = 5;

	public static void main(String[] args) {
		// 連接工廠
		ConnectionFactory connectionFactory;
		// 連接
		Connection connection = null;
		// 會話 接受或者發送消息的線程
		Session session;
		// 消息的目的地
		Destination destination;
		// 消息生產者
		MessageProducer messageProducer;
		// 實例化連接工廠
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD,
				JMSProducer.BROKEURL);

		try {
			// 通過連接工廠獲取連接
			connection = connectionFactory.createConnection();
			// 啟動連接
			connection.start();
			// 創建session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// 創建一個名稱為HelloWorld的消息隊列
			destination = session.createQueue("HelloWorld Jim");
			// 創建消息生產者
			messageProducer = session.createProducer(destination);
			// 發送消息
			sendMessage(session, messageProducer);

			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 發送消息
	 *
	 * @param session
	 * @param messageProducer
	 *            消息生產者
	 * @throws Exception
	 */
	public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
		for (int i = 0; i < JMSProducer.SENDNUM; i++) {
			// 創建一條文本消息
			TextMessage message = session.createTextMessage("ActiveMQ 發送消息" + i);
			System.out.println("發送消息：Activemq 發送消息" + i);

			// 使用xml
//			 ObjectFactory f = new ObjectFactory();
//			 IMSACQ20SvcRqType r = f.createIMSACQ20SvcRqType();
//			 r.setInDate("1061118");
//			 r.setInTime("162052");
//			 r.setCustomerID("X123456789");
//			 r.setQueryType("A");
//			
//			 JAXBElement<IMSACQ20SvcRqType> x = f.createIMSACQ20SvcRq(r);
//			
//			 JAXBContext jc =
//			 JAXBContext.newInstance(IMSACQ20SvcRqType.class);
//			 Marshaller m = jc.createMarshaller();
//			 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			 StringWriter sw = new StringWriter();
//			 m.marshal(x, sw);
//			 //通過消息生產者發出消息
//			message.setText(sw.toString());
//			 sw.close();
//			message.setText(
//					"000139ISCE0000200FD010000000CBCNU012017101115033100000000NUAH991061011NUAH99NUAI5000001MO3R221062838USD003767762692TW1011011602A125301Y");
			messageProducer.send(message);
			// messageProducer.send("TEST");

		}

	}

}