package br.com.consumer;

import br.com.consumer.listener.IMessageReceiver;
import br.com.consumer.testes.ReceberMensagensFilaTest;
import br.com.consumer.tutorial1.Recv;
import br.com.consumer.tutorial2.Worker;
import br.com.consumer.tutorial3.ReceiveLogs;

public class ReceiverApp {

	public static void main(String[] argv) throws Exception {
		IMessageReceiver versao = null;
    	String opt = argv == null || argv.length == 0 ? "" : argv[0]; 
    	switch(opt) {
		case "1":
			versao = new Recv();
			versao.receive();
			break;
		case "2":
			versao = new Worker();
			versao.receive();
			break;
		case "3":
			versao = new ReceiveLogs();
			versao.receive();
			break;

		default:
			ReceberMensagensFilaTest receiverTeste = new ReceberMensagensFilaTest();
			receiverTeste.receberMensagens();
			break;
		}
    	
    	System.out.println("Chamada ao programa " + (opt.isEmpty() ? "default" : "tutorial " + opt) + ". ");

	}
}