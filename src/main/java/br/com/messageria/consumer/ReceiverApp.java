package br.com.messageria.consumer;

import br.com.messageria.consumer.business.IReceberMensagem;
import br.com.messageria.consumer.business.tutorial.one.Recv;
import br.com.messageria.consumer.business.tutorial.two.Worker;

public class ReceiverApp {

  public static void main(String[] argv) throws Exception {
	  IReceberMensagem versao1 = new Recv();
	  IReceberMensagem versao2 = new Worker();
	  versao2.receberMensagem();
	  
  }
}