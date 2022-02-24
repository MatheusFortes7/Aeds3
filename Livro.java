package com.company;


import java.io.*;

public class Livro implements Registro{

    String nome;
    String autor;
    float preco;
    int id;

    public Livro(){
        this("","",00F,-1);
    }

    public Livro(String nome, String autor, float preco) {
        this( nome, autor, preco, -1);
    }

    public Livro(String nome, String autor, float preco, int id) {
        this.nome = nome;
        this.autor = autor;
        this.preco = preco;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream ba_out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba_out);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.autor);
        dos.writeFloat(this.preco);
        return ba_out.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception{
        ByteArrayInputStream ba_in = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(ba_in);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.autor = dis.readUTF();
        this.preco = dis.readFloat();
    }

    @Override
    public String toString() {
        return  "nome='" + nome + '\'' +
                ", autor='" + autor + '\'' +
                ", preco=" + preco +
                ", ID=" + id;
    }
}
