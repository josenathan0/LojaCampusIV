package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;
import bode.produtos.ProdutoDaLoja;

import java.util.List;

public class CupomPague3ELeve4Bode implements CupomDescontoBode {
    @Override
    public double calcularDesconto(double valorOriginal) {
        int quantidadeProdutos = (int) ((int) valorOriginal / ProdutoDaLoja.values()[0].getPreco()); // Supondo que o primeiro produto seja o mais barato
        double valorDesconto = quantidadeProdutos * ProdutoDaLoja.values()[0].getPreco();
        return valorOriginal - valorDesconto;
    }

    @Override
    public double calcularDesconto(double valorOriginal, List<ItemPedido> itens) {
        return calcularDesconto(valorOriginal);
    }
}
