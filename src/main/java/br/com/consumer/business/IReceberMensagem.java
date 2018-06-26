package br.com.consumer.business;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface IReceberMensagem {
	
	public void receberMensagem() throws IOException, TimeoutException;

}
