
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Cliente implements Registro{
    int ID;
  String nome;
  String email;

  public Cliente() {
    this(-1, "", "");
  }

  public Cliente(String n, String e) {
    this(-1, n, e);
  }

  public Cliente(int i, String n, String e) {
    this.ID = i;
    this.nome = n;
    this.email = e;
  }

  public int getId() {
    return this.ID;
  }

  public void setId(int i) {
    this.ID = i;
  }

  public byte[] toByteArray() throws Exception {
    ByteArrayOutputStream ba_out = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(ba_out);
    dos.writeInt(this.ID);
    dos.writeUTF(this.nome);
    dos.writeUTF(this.email);
    return ba_out.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws Exception {
    ByteArrayInputStream ba_in = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(ba_in);
    this.ID = dis.readInt();
    this.nome = dis.readUTF();
    this.email = dis.readUTF();
  }

  public String toString() {
    return "\nID: " + this.ID +
        "\nNome: " + this.nome +
        "\nE-mail: " + this.email;
  }
}
