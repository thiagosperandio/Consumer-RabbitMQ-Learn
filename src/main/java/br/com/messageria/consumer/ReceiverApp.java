package br.com.messageria.consumer;

import br.com.messageria.consumer.business.IReceberMensagem;
import br.com.messageria.consumer.business.TutorialOne;

public class ReceiverApp {

  public static void main(String[] argv) throws Exception {
	  IReceberMensagem versao = new TutorialOne();
	  versao.receberMensagem();
	  
  }
}