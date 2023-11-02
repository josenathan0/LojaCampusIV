package bode.loja.cupom;

public enum TipoDeCupom {
    SEM_DESCONTO(0.0, 1),  // Não há desconto
    PAGUE3_LEVE4(0.25, 2), // 25% de desconto
    ITEM_MAIS_BARATO_GRATIS(1.0, 3); // 100% de desconto (um item grátis)

    private double desconto;
    private int codigo;

    TipoDeCupom(double desconto, int codigo) {
        this.desconto = desconto;
        this.codigo = codigo;
    }

    public double getDesconto() {
        return desconto;
    }

    public int getCodigo() {
        return codigo;
    }
}
