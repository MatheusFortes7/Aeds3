package com.company;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Cliente implements Registro{
    int Id;
    String nome;
    String email;

    public Cliente() {
        this(-1,"","");
    }

    public Cliente(String nome, String email) {
        this(-1, nome, email);
    }

    public Cliente(int ID, String nome, String email) {
        this.Id = ID;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream ba_out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba_out);
        dos.writeInt(this.Id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        return ba_out.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception{
        ByteArrayInputStream ba_in = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(ba_in);
        this.Id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "ID=" + Id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
