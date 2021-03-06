package TP01.aed3;

import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  private RandomAccessFile arquivo;
  private HashExtensivel<ParIDEndereco> indiceDireto;
  private Constructor<T> construtor;
  private int TAMANHO_CABECALHO = 4;

  public Arquivo(String nomePasta, Constructor<T> c) throws Exception {
    arquivo = new RandomAccessFile(nomePasta + "/dados.db", "rw");
    indiceDireto = new HashExtensivel<>(
        ParIDEndereco.class.getConstructor(),
        4,
        nomePasta + "/indiceID.1.db",
        nomePasta + "/indiceID.2.db");
    construtor = c;
    if (arquivo.length() < TAMANHO_CABECALHO) {
      arquivo.seek(0);
      arquivo.writeInt(0);
    }
  }

  public int create(T entidade) throws Exception {
    arquivo.seek(0);
    int ultimoID = arquivo.readInt();
    int novoID = ultimoID + 1;
    entidade.setID(novoID);
    arquivo.seek(0);
    arquivo.writeInt(novoID);

    // Movimenta o ponteiro do arquivo para o ponto de inserção do novo registro
    arquivo.seek(arquivo.length());
    long endereco = arquivo.getFilePointer();

    // Cria o registro no arquivo
    byte[] ba = entidade.toByteArray();
    arquivo.writeByte('#'); // # -> registro válido; * -> registro excluído
    arquivo.writeShort(ba.length);
    arquivo.write(ba);
    indiceDireto.create(new ParIDEndereco(novoID, endereco));
    return novoID;
  };

  public T read(int id) throws Exception {

    ParIDEndereco p = indiceDireto.read(id);
    if (p == null)
      return null;

    arquivo.seek(p.getEndereco());

    byte lapide = arquivo.readByte();
    int tamanho = arquivo.readShort();
    byte[] ba = new byte[tamanho];
    if (lapide == '#') {
      arquivo.read(ba);
      T entidade = construtor.newInstance();
      entidade.fromByteArray(ba);
      if (entidade.getID() == id)
        return entidade;
    }

    System.out.println("Arquivo corrompido.");
    return null;
  };

  public boolean update(T novaEntidade) throws Exception {
    byte[] registro;
    byte[] novoRegistro;
    byte lapide;
    short TAM;
    
    int id = novaEntidade.getID();
    ParIDEndereco p = indiceDireto.read(id);
    if(p == null){
      System.out.println("Id nao encontrado");
      return false;
    }
    long pos = p.getEndereco();
    arquivo.seek(pos);
    lapide = arquivo.readByte();
    TAM = arquivo.readShort();
    registro = new byte[TAM];

    arquivo.read(registro);

    novoRegistro = novaEntidade.toByteArray();

    if(lapide != '*'){
      if(novoRegistro.length <= registro.length){
        arquivo.seek(pos + 3);
        arquivo.write(novoRegistro);
      } else {
        arquivo.seek(pos);
        arquivo.writeByte('*');
        arquivo.seek(arquivo.length());
        pos = arquivo.length();
        arquivo.writeByte('#');
        arquivo.writeShort(novoRegistro.length);
        arquivo.write(novoRegistro);
        indiceDireto.update(new ParIDEndereco(id, pos));
      }
    }
    return false;
  }

  public boolean delete(int id) throws Exception{
    
    try {
      arquivo.seek(0);
    int ultimoId = arquivo.readInt();
    if(ultimoId > id)
      System.out.println("Id nao existente");

    //colocar o endereço do id procurado na variável abaixo
    ParIDEndereco p = indiceDireto.read(id);
    arquivo.seek(p.getEndereco());
    arquivo.writeByte('*');
    
    indiceDireto.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Erro ao deletar!");
    }
    return false;
  };

}
