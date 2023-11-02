package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;
import bode.produtos.ProdutoDaLoja;

import java.util.List;

public class CupomPague3ELeve4Bode implements CupomDescontoBode {
    @Override
    public double calcularDesconto(double valorOriginal) {
        int quantidadeProdutos = (int) (valorOriginal / ProdutoDaLoja.values()[0].getPreco());
        double valorDesconto = quantidadeProdutos * ProdutoDaLoja.values()[0].getPreco();
        return valorOriginal - valorDesconto;
    }

    @Override
    public double calcularDesconto(double valorOriginal, List<ItemPedido> itens) {
        if (itens.size() == 4) {
            double precoMaisBarato = Double.MAX_VALUE;
            int indexMaisBarato = -1;

            for (int i = 0; i < itens.size(); i++) {
                double precoItem = itens.get(i).getProduto().getPreco();
                if (precoItem < precoMaisBarato) {
                    precoMaisBarato = precoItem;
                    indexMaisBarato = i;
                }
            }

            if (indexMaisBarato >= 0) {
                double desconto = itens.get(indexMaisBarato).getSubtotal();
                return valorOriginal - desconto;
            }
        }
        return valorOriginal;
    }
}