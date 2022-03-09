import java.util.Scanner;

public class InteracaoLivros {

  private static Scanner console = new Scanner(System.in);
  private Arquivo<Livro> arqLivros;

  public void MenuLivros() {

    try {
      arqLivros = new Arquivo<>(Livro.class.getConstructor(), "dados/livros/dados.db");

      // Menu principal
      int opcao;
      do {
        System.out.println("\n\nMenu de Livros");
        System.out.println("--------------");
        System.out.println("1) Buscar");
        System.out.println("2) Incluir");
        System.out.println("3) Alterar");
        System.out.println("4) Excluir");
        System.out.println("0) Voltar");
        System.out.print("\nOpção: ");

        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }

        switch (opcao) {
          case 1:
            ConsultarLivro();
            break;
          case 2:
            // IncluirLivro();
            break;
          case 3:
            // AlterarLivro();
            break;
          case 4:
            // ExcluirLivro();
            break;
          case 0:
            break;
          default:
            System.out.println("Opção inválida!");
        }
      } while (opcao != 0);

      arqLivros.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void ConsultarLivro() {
    int id;
    System.out.print("\nID do Livro: ");
    try {
      id = Integer.valueOf(console.nextLine());
      Livro l = arqLivros.read(id);
      System.out.println(l);
    } catch (NumberFormatException e) {
      System.out.println("ID inválido!");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
