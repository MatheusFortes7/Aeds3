import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  private RandomAccessFile arq;
  private Constructor<T> construtor;
  private int TAMANHO_CABECALHO = 4;

  public Arquivo(Constructor<T> c, String nomeArquivo) throws Exception {
    arq = new RandomAccessFile(nomeArquivo, "rw");
    construtor = c;

    // cabeçalho
    if (arq.length() < TAMANHO_CABECALHO) {
      arq.seek(0);
      arq.writeInt(0); // ultimo ID
    }
  }

  public int create(T entidade) throws Exception {

    // Determinar o ID da nova entidade e atualizar o cabeçalho
    arq.seek(0);
    int ultimoID = arq.readInt();
    int novoID = ultimoID + 1;
    arq.seek(0);
    arq.writeInt(novoID);
    entidade.setId(novoID);

    // Encontra o ponto de escrita do novo registro
    arq.seek(arq.length());

    // Escreve o novo registro
    byte[] ba;
    ba = entidade.toByteArray();
    arq.seek(arq.length());
    arq.writeByte(' '); // '*' será usado para registros excluídos
    arq.writeShort((short) ba.length);
    arq.write(ba);
    return novoID;
  }

  public T read(int id) throws Exception {
    short tamanho;
    byte[] ba;
    T entidade = this.construtor.newInstance();
    byte lapide;
    arq.seek(TAMANHO_CABECALHO); // pula os bytes do cabeçalho
    while (arq.getFilePointer() != arq.length()) {
      lapide = arq.readByte();
      tamanho = arq.readShort();
      ba = new byte[tamanho];
      arq.read(ba);
      if (lapide == ' ') {
        entidade.fromByteArray(ba);
        if (entidade.getId() == id)
          return entidade;
      }
    }
    return null;
  }

  public void close() throws Exception {
    arq.close();
  }

}
