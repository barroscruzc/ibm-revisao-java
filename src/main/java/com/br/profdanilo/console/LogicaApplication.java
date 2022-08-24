package com.br.profdanilo.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LogicaApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(LogicaApplication.class, args);

		System.out.println("===== [ App Java Console ] =====");

		BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
 
        // Recebendo nome do usuário(string) via console
		System.out.println("Escreva seu nome:");
        String name = reader.readLine();
		clearConsole();
 
        // Imprimindo o nome na tela
        System.out.println("===== [ Seja Bem Vindo(a), " + name + "! ] =====");
		wait(2);
	
		boolean continua = true;
		HashMap<String, Integer> produtos = new HashMap<String, Integer>();
		ArrayList<String> clientes  = new ArrayList<String>();
		ArrayList<ArrayList<String>> pedidos = new ArrayList<>();

		do {
			clearConsole();
			System.out.println("""
============== [ MENU ] ==============
Escolha uma das opções abaixo: 
1 - Cadastrar produto
2 - Listar produtos 
3 - Cadastrar cliente 
4 - Listar clientes 
5 - Fazer pedido  
6 - Histórico de Pedidos 
7 - Sair 
				""");
			int opcao = Integer.parseInt(reader.readLine());
			wait(1);
			clearConsole();

			switch(opcao){
				case 1:
					System.out.println("""
============= [ NOVO PRODUTO ] =============
Digite o nome do novo produto: """);
					String nomeProduto = reader.readLine();
					if (produtos.containsKey(nomeProduto)){
						System.out.println("Produto " + nomeProduto + " já cadastrado.");
					}
					System.out.println("Digite a quantidade em estoque: ");
					int estoqueProduto = Integer.parseInt(reader.readLine());
					produtos.put(nomeProduto, estoqueProduto);
					clearConsole();
					System.out.println("Produto " + nomeProduto + " cadastrado com sucesso! ");
					wait(2);
				break;
				
				case 2:
					System.out.println("""
============= [ LISTA DE PRODUTOS ] =============""");
					for(Map.Entry<String, Integer> produto : produtos.entrySet()) {
						System.out.println(produto.getKey()+ " : " + produto.getValue() + " unidades em estoque.");
					}
					wait(5);
				break;

				case 3:
					System.out.println("""
=============== [ NOVO CLIENTE ] ===============""");
					System.out.println("Digite o nome do novo cliente: ");
					String novoCliente = reader.readLine();
					clientes.add(novoCliente);
					clearConsole();
					System.out.println("Cliente " + novoCliente + " cadastrado(a) com sucesso! ");
					wait(2);
				break;
				case 4:
					System.out.println("""
============= [ LISTA DE CLIENTES ] =============""");
					int contadorC = 1;
					for(String cliente : clientes) {
						System.out.println(contadorC + " : " + cliente);
						contadorC += 1;
					}
					wait(5);
				break;
				case 5:
					System.out.println("""
=============== [ NOVO PEDIDO ] ===============""");
					for (int i = 0; i < clientes.size(); i++){
						System.out.println((i+1) +  " - " + clientes.get(i));
					}
					System.out.println("Digite o número do cliente: ");
					int idCliente = Integer.parseInt(reader.readLine());
					ArrayList<String> pedidoCliente = new ArrayList<>();
					pedidoCliente.add(clientes.get(idCliente - 1));
					boolean voltar = false;
					do {
						System.out.println("""
============= [ SELECIONAR PRODUTOS ] =============""");
						for(Map.Entry <String, Integer> produto : produtos.entrySet()) {
							System.out.println(produto.getKey()+ " : " + produto.getValue() + " unidades em estoque.");
						}
						blankLine();
						System.out.println("Digite o nome do produto: ");
						String idProduto = reader.readLine();
						System.out.println("Quantidade: ");
						Integer qtdProduto = Integer.parseInt(reader.readLine());
						produtos.compute(idProduto, (produto, estoque) -> estoque -= qtdProduto);
						pedidoCliente.add(idProduto + " - " + qtdProduto.toString());
						clearConsole();
						System.out.println("Atualização: " + idProduto + " - " + produtos.get(idProduto).toString() + " unidades em estoque.");
						blankLine();
						System.out.println("Adicionar mais itens ao pedido? \n 1 - Sim \n 2 - Não");
						int opcao2 = Integer.parseInt(reader.readLine());
						switch (opcao2){
							case 1:
							clearConsole();
							break;
							case 2:
							clearConsole();
							pedidos.add(pedidoCliente);
							System.out.println("Pedido cadastrado com sucesso!");
							wait(2);
							voltar = true;
							break;
						}
					} while (!voltar);
				break;
				case 6:
					clearConsole();
					System.out.println("""
=========== [ HISTÓRICO DE PEDIDOS ] ===========""");
					for(ArrayList<String> registro : pedidos){
						for(int i = 0; i < registro.size(); i++){
							System.out.println(registro.get(i));
						}
						blankLine();
					}
					wait(5);
					clearConsole();
				break;
				case 7:
				clearConsole();
				System.out.println("""
==== [ Obrigado por usar: App Java Console! ] ====
================ [ Encerrando... ] ===============""");
				wait(2);
				continua = false;
				break;
				default:
				clearConsole();
				System.out.println("Opção inválida!");
				wait(2);
				clearConsole();
				break;
			}
		} while (continua);
	}

	//método para limpar tela
	public final static void clearConsole() throws InterruptedException, IOException
	{
		if (System.getProperty("os.name").contains("Windows")){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //para Windows
		} else {
            Runtime.getRuntime().exec("clear");  //para Linux
		}
	}

	public final static void wait(int seconds) throws InterruptedException{
		TimeUnit.SECONDS.sleep(seconds);
	}

	public final static void blankLine(){
		System.out.println("""
--------------------------------------------------""");
	}
}
