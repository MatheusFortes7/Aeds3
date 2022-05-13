package entidades.livros;

import aed3.Arquivo;
  import aed3.HashExtensivel;

  public class ArquivoLivro extends Arquivo<Livro> {

    HashExtensivel<ParNomeID> indiceIndiretoEmail;

    public ArquivoLivro(String nomePasta) throws Exception {
      super(nomePasta, Livro.class.getConstructor());
      indiceIndiretoEmail = new HashExtensivel<>(
          ParNomeID.class.getConstructor(),
          4,
          nomePasta + "/indiceEmail.1.db",
          nomePasta + "/indiceEmail.2.db");
    }

    // ---------------------
    // CREATE
    // ---------------------
    @Override
    public int create(Livro livro) throws Exception {
      int id = super.create(livro);
      livro.setID(id);
      indiceIndiretoEmail.create(
          new ParNomeID(livro.getTitulo(), id));
      return id;
    }

    // ---------------------
    // READ (email)
    // ---------------------
    public Livro read(String titulo) throws Exception {
      ParNomeID p = indiceIndiretoEmail.read(Math.abs(titulo.hashCode()));
      if (p == null)
        return null;
      return read(p.getID()); // método da superclasse
    }

    public boolean update(Livro novoLivro) throws Exception {
      try {
        Livro livroAntigo = super.read(novoLivro.getID());

        if(livroAntigo != null && super.update(novoLivro)) {
          indiceIndiretoEmail.delete(livroAntigo.getTitulo().hashCode());
          indiceIndiretoEmail.create(new ParNomeID(novoLivro.getTitulo(), novoLivro.getID()));
          return true;
        }
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erro ao alterar!");
      }

      return false;
    }

    //!-------------------------
    //!   ERRO AQUI NO DELETE
    //!-------------------------
    
    public boolean delete(int id) throws Exception {
      try{
        Livro livro = super.read(id);
        if(livro != null && super.delete(id)){
          indiceIndiretoEmail.delete(livro.getTitulo().hashCode());
          return true;
        }
        return false;

      } catch (Exception e){
        e.printStackTrace();
        System.out.println("Erro ao deletar!");
      }

      return false;
    }

  }

