package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;

import java.util.List;

public class CupomSemDesconto implements CupomDescontoBode {
    @Override
    public double calcularDesconto(double valorOriginal) {
        // Nenhum desconto, retorna o valor original
        return valorOriginal;
    }

    @Override
    public double calcularDesconto(double valorOriginal, List<ItemPedido> itens) {
        return 0;
    }
}
