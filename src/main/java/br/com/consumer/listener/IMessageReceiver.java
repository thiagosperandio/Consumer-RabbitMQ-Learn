package br.com.consumer.listener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface IMessageReceiver {
	
	public void receive() throws IOException, TimeoutException, IllegalArgumentException;

}
