package br.com.consumer;

import br.com.consumer.business.IReceberMensagem;
import br.com.consumer.business.tutorial1.*;
import br.com.consumer.business.tutorial2.*;

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
	  	default:
	  		versao = new Worker();
	  		break;
	  }
	  versao.receberMensagem();
	  
  }
}