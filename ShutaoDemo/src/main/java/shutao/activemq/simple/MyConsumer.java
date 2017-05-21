package shutao.activemq.simple;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Data from producer, to the active MQ, then to the consumer to process.
 * 
 * @author Shutao
 * 
 */
public class MyConsumer {
	public static void main(String[] args) throws JMSException {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		// Create a Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// Create Consumer
		Queue testQueue = session.createQueue("ShuTaoTest");
		MessageConsumer consumer = session.createConsumer(testQueue);

		// If no message, will wait 5 seconds.
		// If want to get multiple messages, loop here.
		TextMessage message = (TextMessage) consumer.receive(5 * 1000);
		String msg = message == null ? "" : message.getText();
		System.out.println("Message received: " + msg);

		connection.stop();
		connection.close();
	}
}
