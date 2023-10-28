package bode.loja.cupom;

public enum TipoDeCupom {
    SEM_DESCONTO(0.0),  // Não há desconto
    PAGUE3_LEVE4(0.25), // 25% de desconto
    ITEM_MAIS_BARATO_GRATIS(1.0); // 100% de desconto (um item grátis)

    private double desconto;

    TipoDeCupom(double desconto) {
        this.desconto = desconto;
    }

    public double getDesconto() {
        return desconto;
    }
}
