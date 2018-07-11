package br.com.consumer;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import br.com.consumer.listener.IMessageReceiver;
import br.com.consumer.testes.ReceberMensagensFilaTest;
import br.com.consumer.tutorial1.Recv;
import br.com.consumer.tutorial2.Worker;
import br.com.consumer.tutorial3.ReceiveLogs;

public class ReceiverApp {

	public static void main(String[] argv) {
		IMessageReceiver versao = null;
    	String opt = argv == null || argv.length == 0 ? "" : argv[0]; 
    	try {
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
		} catch (ConnectException e) {
			e.printStackTrace();
			System.out.println("Erro de ConnectException: " + e.getMessage() + " - " + e.getCause());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Erro de IllegalArgumentException: " + e.getMessage() + " - " + e.getCause());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro de IOException: " + e.getMessage() + " - " + e.getCause());
		} catch (TimeoutException e) {
			e.printStackTrace();
			System.out.println("Erro de TimeoutException: " + e.getMessage() + " - " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro de Exception: " + e.getMessage() + " - " + e.getCause());
		}
    	
    	System.out.println("Chamada ao programa " + (opt.isEmpty() ? "default" : "tutorial " + opt) + ". ");

	}
}