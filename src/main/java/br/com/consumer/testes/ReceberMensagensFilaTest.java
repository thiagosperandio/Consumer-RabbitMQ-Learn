package br.com.consumer.testes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import br.com.consumer.listener.IMessageReceiver;
import br.com.consumer.listener.MessageReceiver;

public class ReceberMensagensFilaTest {

	private final static String QUEUE_NAME = "task_queue";
	private final static String HOST_NAME = "localhost";
	
	private IMessageReceiver receiver = new MessageReceiver(QUEUE_NAME, HOST_NAME);
	
	public void receberMensagens() throws IOException, TimeoutException, IllegalArgumentException {
		receiver.receive();
	}	
}
