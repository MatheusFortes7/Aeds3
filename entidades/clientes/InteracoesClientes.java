package entidades.clientes;

import java.io.File;
import java.util.Scanner;

import aed3.Arquivo;

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

    public void menuClientes() throws Exception {
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
              incluirCliente();
              break;
            case 2:
              buscarCliente();
              break;
            case 3:
              alterarCliente();
              break;
            case 4:
              excluirCliente();
              break;
            case 0:
              break;
            default:
              System.out.println("Opção inválida");
          }
        } while (opcao != 0);
    }

    private void incluirCliente() {
      Cliente novoCliente;
    try {
      novoCliente = leCliente();
    } catch (Exception e) {
      System.out.println("Dados inválidos");
      return;
    }

    int id;
    try {
      id = arqClientes.create(novoCliente);
    } catch (Exception e) {
      System.out.println("Cliente não pode ser criado");
      e.printStackTrace();
      return;
    }

    System.out.println("\nCliente criado com o ID " + id);

    }

    private void buscarCliente() {
      int id;
      System.out.print("\nID do Cliente: ");
      try {
        id = Integer.valueOf(console.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
        return;
      }
  
      try {
        Cliente c = arqClientes.read(id);
        mostraCLiente(c);
      } catch (Exception e) {
        System.out.println("Erro no acesso ao arquivo");
        e.printStackTrace();
      }
  
    }
    
    private void excluirCliente() {
      try {
        buscarCliente();
        System.out.println("\nConfirme o id do cliente que você deseja excluir");
        int idDelete = Integer.valueOf(console.nextLine());
        arqClientes.delete(idDelete);
      } catch (Exception e) {
        System.out.println("Nao foi possivel a remoção do livro.");
        e.printStackTrace();
      }
    }

    private void alterarCliente() throws Exception {
      Cliente c = new Cliente();
      buscarCliente();
      System.out.println("Entre com o id do cliente que voce deseja alterar");
      int idAlt = Integer.valueOf(console.nextLine());
      System.out.println("Entre com o novo nome do cliente:");
      String novoNome = console.nextLine();
      System.out.println("Agora entre com o novo email do cliente:");
      String novoEmail = console.nextLine();
      c.setEmail(novoEmail);
      c.setNome(novoNome);
      arqClientes.update(c);
    }

}
