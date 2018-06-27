package br.com.consumer.listener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.consumer.AppUtils;

public class MessageReceiver implements IMessageReceiver {

	final static boolean DURABLE_QUEUE = true; // Fila deve ser persistida no servidor, mantendo-a caso ele páre.
	final static int PREFETCH_COUNT = 1; // Deve receber apenas X mensagem(ns) por vez para cada listeners.
	final static boolean AUTO_ACK = false; // Só confirma recebimento da mensagem ao servidor se não der nenhum erro até o fim.

	private String queue, host;
	Integer port;
	private JsonObject message;

	public MessageReceiver() {
		super();
	}

	public MessageReceiver(String queue, String host) {
		super();
		this.queue = queue;
		this.host = host;
	}

	public MessageReceiver(String queue, String host, Integer port) {
		super();
		this.queue = queue;
		this.host = host;
		this.port = port;
	}

	public MessageReceiver(String queue, String host, JsonObject message) {
		super();
		this.queue = queue;
		this.host = host;
		this.message = message;
	}

	public MessageReceiver(String queue, String host, Integer port, JsonObject message) {
		super();
		this.queue = queue;
		this.host = host;
		this.port = port;
		this.message = message;
	}

	public void receive() throws IOException, TimeoutException, IllegalArgumentException {
		if(getQueue() == null || getHost() == null) {
			throw new IllegalArgumentException("Incorrect or imcomplete data:"
					+ " fila: " + getQueue()
					+ ", host: " + getHost()
					+ ", port (optional): " + getPort()
					);
		}
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(getHost());
		if (getPort() != null && getPort() > 0) {
			factory.setPort(getPort());	
		}

		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(getQueue(), DURABLE_QUEUE, false, false, null);

		System.out.println(" [*] Waiting for messages.");

		channel.basicQos(PREFETCH_COUNT);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				setMessage(deserializeAsJson(body));
				System.out.println((new Date()) + " [x] Received: '" 
		    			+ AppUtils.reduceStringAt(message.toString(), 50) + "(...)'"); // exibir x caracteres no console
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(getQueue(), AUTO_ACK, consumer);
	}

	JsonObject deserializeAsJson(byte[] content) throws IOException {
		try (ByteArrayInputStream contentStream = new ByteArrayInputStream(content);
				JsonReader reader = Json.createReader(contentStream)) {
			return reader.readObject();
		}
	}
	
	// getters and setters

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public JsonObject getMessage() {
		return message;
	}

	public void setMessage(JsonObject message) {
		this.message = message;
	}

}
