package com.ian.tools.jms.activemq;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JmsMessage implements Serializable {
	private static final long serialVersionUID = -5695631219512511768L;
	private static int READING_BUFFER = 65536;

	public static String MSG_ID_PATTERN = "JMSMessageID='%s'";

	public static String CORR_MSG_ID_PATTERN = "JMSCorrelationID='%s'";
	
	private QueueConnectionFactory toEAIFactory;
	private InitialContext ctx;
	private QueueConnectionFactory fromEAIFactory;

	
	public String getStringMessage(Message inMessage) throws JMSException {
		try {
			String strMessage = null;
			if ((inMessage instanceof BytesMessage)) {
				byte[] rawdata = new byte[READING_BUFFER];
				BytesMessage rawMessage = (BytesMessage) inMessage;
				rawMessage.reset();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int dataLength;
				while ((dataLength = rawMessage.readBytes(rawdata)) != -1) {
					// int dataLength;
					bos.write(rawdata, 0, dataLength);
				}
				strMessage = bos.toString();
			} else if ((inMessage instanceof TextMessage)) {
				strMessage = ((TextMessage) inMessage).getText();
			} else if ((inMessage instanceof StreamMessage)) {
				byte[] rawdata = (byte[]) null;
				strMessage = new String(rawdata);
			} else {
				if ((inMessage instanceof ObjectMessage)) {
					throw new JMSException("Retrieved the wrong type of message(ObjectMessage)");
				}
				if ((inMessage instanceof MapMessage)) {
					throw new JMSException("Retrieved the wrong type of message(MapMessage)");
				}
			}
			return strMessage;
		} catch (JMSException e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	public QueueConnectionFactory getConnectionFactoryFromJNDI(Context ctx, String name) throws JMSException {
		QueueConnectionFactory factory = null;
		try {
			factory = (QueueConnectionFactory) ctx.lookup(name);
		} catch (Exception e) {
			JMSException je = new JMSException(
					"Unable to retrieve the QueueConnectionFactory '" + name + "' object from JNDI");
			je.setLinkedException(e);
			throw je;
		}
		return factory;
	}

	
	public InitialContext initJNDI() throws JMSException {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} catch (Exception e) {
			JMSException je = new JMSException("Cannot create JNDI InitialContext!");
			je.setLinkedException(e);
			throw je;
		}
		return ctx;
	}

	
	public Queue getQueueFromJNDI(Context ctx, String name) throws JMSException {
		Queue ioQueue = null;
		try {
			ioQueue = (Queue) ctx.lookup(name);
		} catch (Exception e) {
			JMSException je = new JMSException("Unable to retrieve the Queue '" + name + "' from JNDI");
			je.setLinkedException(e);
			throw je;
		}
		return ioQueue;
	}

	
	public String execute(String outMessage, boolean waitForResp) throws JMSException {
		Queue toEAIQueue = null;
		Queue fromEAIQueue = null;
		QueueSession toEAISession = null;
		QueueSession fromEAISession = null;
		QueueConnection toEAIConnection = null;
		QueueConnection fromEAIConnection = null;
		QueueSender queueSender = null;
		QueueReceiver queueReceiver = null;
		boolean transacted = false;
		try {
			if (this.toEAIFactory == null) {
				this.ctx = initJNDI();
				this.toEAIFactory = getConnectionFactoryFromJNDI(this.ctx, getToEAIQCF());
			}

			toEAIConnection = this.toEAIFactory.createQueueConnection();
			toEAIConnection.start();

			toEAISession = toEAIConnection.createQueueSession(transacted, 1);

			toEAIQueue = getQueueFromJNDI(this.ctx, getToEAIQ());
			queueSender = toEAISession.createSender(toEAIQueue);
			TextMessage outMsg = toEAISession.createTextMessage();

			if (waitForResp) {
				fromEAIQueue = getQueueFromJNDI(this.ctx, getFromEAIQ());

				outMsg.setJMSExpiration(getJMSExpirationTime());
				outMsg.setJMSReplyTo(fromEAIQueue);
			}

			outMsg.setText(outMessage);
			queueSender.send(outMsg);

			if (waitForResp) {
				if (this.fromEAIFactory == null) {
					this.fromEAIFactory = getConnectionFactoryFromJNDI(this.ctx, getFromEAIQCF());
				}

				fromEAIConnection = this.fromEAIFactory.createQueueConnection();
				fromEAIConnection.start();

				fromEAISession = fromEAIConnection.createQueueSession(transacted, 1);

				String selector = String.format(CORR_MSG_ID_PATTERN, new Object[] { outMsg.getJMSMessageID() });

//				LogUtils.debug(this, "JMS message id: '" + selector + "'");

				queueReceiver = fromEAISession.createReceiver(fromEAIQueue, selector);
			}

			if (waitForResp) {
				toEAIConnection.start();

				Message inMsg = queueReceiver.receive(getWaitingTime());

				if (inMsg == null) {
					throw new JMSException("Failed to get message back again", getErrorCatalog() + getErrorCode());
				}
				String str1 = new String(getStringMessage(inMsg));
				return str1;
			}
			return null;
		} catch (JMSException je) {
			throw je;
		} finally {
			try {
				if (queueReceiver != null)
					queueReceiver.close();
				if (queueSender != null)
					queueSender.close();
				if (toEAISession != null)
					toEAISession.close();
				if (fromEAISession != null)
					fromEAISession.close();
				if (toEAIConnection != null)
					toEAIConnection.close();
				if (fromEAIConnection != null)
					fromEAIConnection.close();
			} catch (JMSException je) {
//				LogUtils.wait(this, "can not close resource" + je.getMessage());
				je.printStackTrace();
			}
		}
		//throw localObject;
	}

	private String getErrorCatalog() {
		return "";
	}

	private String getErrorCode() {
		return "";
	}

	private long getWaitingTime() {
		return 0;
	}

	private String getFromEAIQCF() {
		return null;
	}

	private String getFromEAIQ() {
		return null;
	}

	private long getJMSExpirationTime() {
		return 0;
	}

	private String getToEAIQ() {
		return null;
	}

	private String getToEAIQCF() {
		return "java:comp/env/jdbc/twnb";
	}
	
	
	
	
	public static void main(String[] args) throws JMSException 
	{
        JmsMessage jms = new JmsMessage();
        jms.execute("TEST", true);
		
	}
	       
}
