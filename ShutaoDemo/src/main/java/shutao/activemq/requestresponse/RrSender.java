package shutao.activemq.requestresponse;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class RrSender {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination requestQueue = session.createQueue("request");
		// Create producer to send message
		MessageProducer producer = session.createProducer(requestQueue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// Create a temporary queue, and create a consumer for reply
		Destination tempDest = session.createTemporaryQueue();
		MessageConsumer responseConsumer = session.createConsumer(tempDest);

		// Set a temporary message listener
		responseConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				try {
					System.out.println("Received message： " + ((TextMessage) arg0).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		// Request Message
		TextMessage txtMessage = session.createTextMessage();
		txtMessage.setText("Hello World ! From RrSender.");

		// Set the message head, let the reply go to the temporary queue we just
		// created.
		txtMessage.setJMSReplyTo(tempDest);

		// Set a unique ID for the request message. (During multi-thread
		// situation, use this ID to sync request & response)
		String correlationId = UUID.randomUUID().toString();
		txtMessage.setJMSCorrelationID(correlationId);
		producer.send(txtMessage);
	}
}