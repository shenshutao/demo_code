package shutao.activemq.requestresponse;

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

public class RrReceiver {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination requestQueue = session.createQueue("request");

		// Create producer for reply
		final MessageProducer replyProducer = session.createProducer(null);
		replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// Create consumer to consume message from request queue.
		MessageConsumer consumer = session.createConsumer(requestQueue);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage response = session.createTextMessage();
					if (message instanceof TextMessage) {
						TextMessage txtMsg = (TextMessage) message;
						String messageText = txtMsg.getText();
						System.out.println("Received message: " + messageText);
						response.setText("This is reply, From RrReceiver");
					}
					// Use the information from the request message head to reply.
					response.setJMSCorrelationID(message.getJMSCorrelationID());
					replyProducer.send(message.getJMSReplyTo(), response);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
}