package br.com.consumer.listener;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

public interface IMessageReceiver {
	
	public void receive() 
			throws ConnectException, IOException, TimeoutException, IllegalArgumentException;;

}
