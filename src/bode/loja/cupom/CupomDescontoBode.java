package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;

import java.util.List;

public interface CupomDescontoBode {
    // Método para calcular o desconto com base no valor original
    double calcularDesconto(double valorOriginal);

    double calcularDesconto(double valorOriginal, List<ItemPedido> itens);
}
