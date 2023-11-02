package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;

import java.util.List;

public class CupomItemMaisBaratoGratis implements CupomDescontoBode {
    @Override
    public double calcularDesconto(double valorOriginal) {
        return 0;
    }

    @Override
    public double calcularDesconto(double valorOriginal, List<ItemPedido> itens) {
        if (itens.size() >= 5) {
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