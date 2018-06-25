package br.com.messageria.consumer.business.tutorial.two;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.messageria.consumer.business.IReceberMensagem;

public class Worker implements IReceberMensagem {

	private final static String QUEUE_NAME = "task_queue";
	
	public void receberMensagem() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

    	boolean durable = true;
    	channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	    final Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	    	    String message = new String(body, "UTF-8");

	    	    System.out.println(" [x] Received '" + message + "'");
	    	    try {
	    	      doWork(message);
	    	    } catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
	    	      System.out.println(" [x] Done");
	    	      channel.basicAck(envelope.getDeliveryTag(), false);
	    	    }
	    	  }
	    	};
    	boolean autoAck = false; 
    	channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
	
	private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') Thread.sleep(1000);
	    }
	}
	
	
}
