package br.com.consumer.tutorial2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.consumer.AppUtils;
import br.com.consumer.listener.IMessageReceiver;

public class Worker implements IMessageReceiver {

	private final static String QUEUE_NAME = "task_queue";

	public void receive() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		boolean durable = true; // Fila deve ser persistida no servidor, mantendo-a caso ele páre.
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		int prefetchCount = 1;
		channel.basicQos(prefetchCount); // Deve receber apenas X mensagem(ns) por vez, melhorando o balanceamento entre
											// worker listeners.

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println(" [x] Received '" + message + "'");
				try {
					AppUtils.doSleepWorkByDots(message);
				} finally {
					System.out.println(" [x] Done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autoAck = false; // Só confirma recebimento da mensagem se não der nenhum erro até o fim.
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}

}
