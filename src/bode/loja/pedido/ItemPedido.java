package bode.loja.pedido;

import bode.produtos.ProdutoDaLoja;

public class ItemPedido {
    private ProdutoDaLoja produto;
    private int quantidade;

    public ItemPedido(ProdutoDaLoja produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ProdutoDaLoja getProduto() {
        return produto;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }


}
