  package entidades.clientes;

  import aed3.Arquivo;
  import aed3.HashExtensivel;

  public class ArquivoClientes extends Arquivo<Cliente> {

    HashExtensivel<ParEmailID> indiceIndiretoEmail;

    public ArquivoClientes(String nomePasta) throws Exception {
      super(nomePasta, Cliente.class.getConstructor());
      indiceIndiretoEmail = new HashExtensivel<>(
          ParEmailID.class.getConstructor(),
          4,
          nomePasta + "/indiceEmail.1.db",
          nomePasta + "/indiceEmail.2.db");
    }

    // ---------------------
    // CREATE
    // ---------------------
    @Override
    public int create(Cliente cliente) throws Exception {
      int id = super.create(cliente);
      cliente.setID(id);
      indiceIndiretoEmail.create(
          new ParEmailID(cliente.getEmail(), id));
      return id;
    }

    // ---------------------
    // READ (email)
    // ---------------------
    public Cliente read(String email) throws Exception {
      ParEmailID p = indiceIndiretoEmail.read(Math.abs(email.hashCode()));
      if (p == null)
        return null;
      return read(p.getID()); // método da superclasse
    }

    public boolean update(Cliente novoCliente) throws Exception {
      try {
        Cliente clienteAntigo = super.read(novoCliente.getID());

        if(clienteAntigo != null && super.update(novoCliente)) {
          indiceIndiretoEmail.delete(clienteAntigo.getEmail().hashCode());
          indiceIndiretoEmail.create(new ParEmailID(novoCliente.getEmail(), novoCliente.getID()));
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
        Cliente cliente = super.read(id);
        if(cliente != null && super.delete(id)){
          indiceIndiretoEmail.delete(cliente.getEmail().hashCode());
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
