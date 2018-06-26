package br.com.consumer;

import br.com.consumer.business.IReceberMensagem;
import br.com.consumer.business.tutorial1.Recv;
import br.com.consumer.business.tutorial2.Worker;
import br.com.consumer.business.tutorial3.ReceiveLogs;

public class ReceiverApp {

  public static void main(String[] argv) 
		  throws Exception {
	  IReceberMensagem versao = null;
	  switch (argv[0]) {
	  	case "1":
	  		versao = new Recv();
	  		break;
	  	case "2":
	  		versao = new Worker();
	  		break;
	  	case "3":
	  		versao = new ReceiveLogs();
	  		break;
	  		
	  	default:
	  		versao = new ReceiveLogs();
	  		break;
	  }
	  
	  versao.receberMensagem();
	  
  }
}