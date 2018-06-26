package br.com.consumer.business.tutorial3;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.consumer.business.IReceberMensagem;

public class ReceiveLogs implements IReceberMensagem {
	  private static final String EXCHANGE_NAME = "logs";
	
	public void receberMensagem() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    final Connection connection = factory.newConnection();
	    final Channel channel = connection.createChannel();
  
    	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    	String queueName = channel.queueDeclare().getQueue();
    	channel.queueBind(queueName, EXCHANGE_NAME, "");
    	
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C"); 

	    final Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	    	    String message = new String(body, "UTF-8");

	    	    System.out.println(" [x] Received '" + message + "'");
	    	    try {
	    	      doWork(message);
	    	    } finally {
	    	      System.out.println(" [x] Done");
	    	      channel.basicAck(envelope.getDeliveryTag(), false);
	    	    }
	    	  }
	    	};
	    	
    	boolean autoAck = false; // Só confirma recebimento da mensagem se não der nenhum erro até o fim.
    	channel.basicConsume(queueName, autoAck, consumer);
	}
	
  private static void doWork(String task) {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
	
	
}
