package bode.produtos;

public enum ProdutoDaLoja {
    BATATA_FRITA(8.00),
    PASTEL(10.00),
    CHOCOLATE_AO_LEITE(8.00),
    AGUA(3.00),
    REFRIGERANTE(4.00),
    PAO_BOLA(7.00),
    PORCAO_DE_QUEIJO(12.00),
    CARNE_HAMBURGUER(12.00),
    BATATA_COM_QUEIJO(20.75),
    HAMBURGUER(23.25),
    PASTEL_COM_REFRIGERANTE(10.50),
    CHOCOLATE_COM_AGUA(8.25),
    SUCO_LARANJA(4.00),
    SALADA_FRUTAS(8.00),
    TORRADA_PAO_FORMA(3.00),
    PORCAO_QUEIJO(10.00),
    BATATA_SUCO(14.00),
    TORRADA_QUEIJO_SUCO(15.00),
    TORRADA_SUCO(5.00),
    BATATA_QUEIJO_DERRETIDO(20.00);

    private double preco;

    ProdutoDaLoja(double preco) {
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public int getCodigo() {
        return ordinal();
    }

    public String getDescricao() {
        return toString();
    }
}
