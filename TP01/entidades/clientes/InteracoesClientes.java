package TP01.entidades.clientes;

import java.io.File;
import java.util.Scanner;

import TP01.aed3.Arquivo;

public class InteracoesClientes {

    private static Scanner console = new Scanner(System.in);
    private Arquivo<Cliente> arqClientes;

    public InteracoesClientes(){
        try {
            File d;
            d = new File("dados");
            if(!d.exists())
                d.mkdir();
            d = new File("dados/clientes");
            if(!d.exists())
                d.mkdir();
            arqClientes = new Arquivo<>("dados/clientes", Cliente.class.getConstructor());

        } catch (Exception e) {
            System.out.println("Arquivo não pode ser aberto ou criado.");
            e.printStackTrace();
        }
    }

    public Cliente leCliente() throws Exception {
        System.out.println("\nNome: ");
        String nome = console.nextLine();
        System.out.println("\nEmail: ");
        String email = console.nextLine();
        Cliente l = new Cliente(nome, email);
        return l;
    }

    public void mostraCLiente(Cliente l){
        System.out.println(
            "\nNome: " + l.getNome() + 
            "\nEmail: " + l.getEmail());
    }

    public void menuClientes() {
        int opcao;
        do {
          System.out.println("\nMENU DE CLIENTES");
          System.out.println("\n1) Incluir cliente");
          System.out.println("2) Buscar cliente");
          System.out.println("3) Alterar cliente");
          System.out.println("4) Excluir cliente");
          System.out.println("\n0) Retornar ao menu anterior");
    
          System.out.print("\nOpção: ");
          try {
            opcao = Integer.valueOf(console.nextLine());
          } catch (NumberFormatException e) {
            opcao = -1;
          }
    
          switch (opcao) {
            case 1:
              //incluirCliente();
              break;
            case 2:
              //buscarCliente();
              break;
            case 3:
              // alterarCliente();
              break;
            case 4:
              // excluirCliente();
              break;
            case 0:
              break;
            default:
              System.out.println("Opção inválida");
          }
        } while (opcao != 0);
    }

}
