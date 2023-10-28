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
        // Implemente a lógica para tornar o item mais barato grátis a partir de 4 itens
        if (itens.size() >= 4) {
            double precoMaisBarato = Double.MAX_VALUE;
            ItemPedido itemMaisBarato = null;

            for (ItemPedido item : itens) {
                double precoItem = item.getProduto().getPreco();
                if (precoItem < precoMaisBarato) {
                    precoMaisBarato = precoItem;
                    itemMaisBarato = item;
                }
            }

            if (itemMaisBarato != null) {
                // Torna o item mais barato grátis
                double desconto = itemMaisBarato.getSubtotal();
                return valorOriginal - desconto;
            }
        }

        // Retorna o valor original se o desconto não se aplicar
        return valorOriginal;
    }
}
