package shutao.activemq.simple;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Download ActiveMQ from http://activemq.apache.org/download.html
 * Install & start the service in your machine.
 *  
 * @author Shutao
 * 
 */
public class MyProducer {

	public static void main(String[] args) throws JMSException {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		// Create a Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// Create Queue
		Queue testQueue = session.createQueue("ShuTaoTest");
		MessageProducer testProducer = session.createProducer(testQueue);

		System.out.println("Sending Message ...");
		TextMessage textMessage = session.createTextMessage("Hello Wolrd !");
		testProducer.send(textMessage);
		System.out.println("Message sent to the queue 'ShuTaoTest'.");

		connection.stop();
		connection.close();
	}
}
