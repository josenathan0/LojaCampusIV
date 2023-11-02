package bode.loja.cupom;

public enum TipoDeCupom {
    SEM_DESCONTO(0.0, 0),  // Não há desconto
    PAGUE3_LEVE4(0.25, 1), // 25% de desconto
    ITEM_MAIS_BARATO_GRATIS(1.0, 2); // 100% de desconto (um item grátis)

    private double desconto;
    private int codigo;

    TipoDeCupom(double desconto, int codigo) {
        this.desconto = desconto;
        this.codigo = codigo;
    }

    public double getDesconto() {
        return desconto;
    }
}
