package com.teste.service;

import java.util.Enumeration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

@Named
public class JMSBean {
	
	//Mesmo nome que foi colocado no applicationContext
	@Inject
	ActiveMQConnectionFactory jmsConnectionFactory;
	
	public int submit(String id) throws Exception{
		Connection connection = jmsConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test");
		
		QueueBrowser browser = session.createBrowser(queue);
		Enumeration e = browser.getEnumeration();
		int nQueue = 0;
		while (e.hasMoreElements()) {
			e.nextElement();
			nQueue++;
		}
		
		MessageProducer producer = session.createProducer(queue);
		TextMessage textMessage = session.createTextMessage(id);
		producer.send(textMessage);
		
		connection.stop();
		
		return nQueue;
	}
	
	public int jobPosition(String id) throws JMSException{
		Connection connection = jmsConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test");
		QueueBrowser browser = session.createBrowser(queue);
		Enumeration e = browser.getEnumeration();
		int i = 0;
		while (e.hasMoreElements()) {
			TextMessage message = (TextMessage) e.nextElement();
			String strMessage = message.getText();
			if(id.equals(strMessage)) return i;
			i++;
		}

		return -1;
	}
}
