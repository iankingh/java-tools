package com.ian.tools.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息的消費者（接受者）
 *
 */
public class JMSConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;			//默認連接用戶名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;		//默認連接密碼
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;	//默認連接地址

    public static void main(String[] args) {
    	ConnectionFactory connectionFactory;//連接工廠
    	Connection connection = null;//連接

    	Session session;//會話 接受或者發送消息的線程
    	Destination destination;//消息的目的地

    	MessageConsumer messageConsumer;//消息的消費者

    	//實例化連接工廠
        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);

        try {
        	//通過連接工廠獲取連接
        	connection = connectionFactory.createConnection();
        	//啟動連接
        	connection.start();
        	//創建session
        	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        	//創建一個連接HelloWorld的消息隊列
        	destination = session.createQueue("HelloWorld Jim");
        	//創建消息消費者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("收到的消息:" + textMessage.getText());
                }else {
                    break;
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
