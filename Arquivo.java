import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {

  private RandomAccessFile arq;
  private Constructor<T> construtor;

  public Arquivo(Constructor<T> c, String nomeArquivo) throws Exception {
    arq = new RandomAccessFile(nomeArquivo, "rw");
    construtor = c;
  }

  public void create(T entidade) throws Exception {
    byte[] ba;
    ba = entidade.toByteArray();
    arq.seek(arq.length());
    arq.writeShort((short) ba.length);
    arq.write(ba);
  }

  public T read(int id) throws Exception {
    short tamanho;
    byte[] ba;
    T entidade = this.construtor.newInstance();
    arq.seek(0);
    while (arq.getFilePointer() != arq.length()) {
      tamanho = arq.readShort();
      ba = new byte[tamanho];
      arq.read(ba);
      entidade.fromByteArray(ba);
      if (entidade.getID() == id)
        return entidade;
    }
    return null;
  }

}
