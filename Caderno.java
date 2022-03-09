

public class Caderno {
    int NmrPag;
    float preco;
    boolean capaDura;

    public Caderno(int nmrPag, float preco, boolean capaDura) {
        NmrPag = nmrPag;
        this.preco = preco;
        this.capaDura = capaDura;
    }

    public int getNmrPag() {
        return NmrPag;
    }

    public void setNmrPag(int nmrPag) {
        NmrPag = nmrPag;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public boolean isCapaDura() {
        return capaDura;
    }

    public void setCapaDura(boolean capaDura) {
        this.capaDura = capaDura;
    }

    @Override
    public String toString() {
        return "Caderno{" +
                "NmrPag=" + NmrPag +
                ", preco=" + preco +
                ", capaDura=" + capaDura +
                '}';
    }
}
