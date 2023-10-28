package bode.loja.cupom;

import bode.loja.pedido.ItemPedido;
import bode.produtos.ProdutoDaLoja;

import java.util.List;

public class CupomPague3ELeve4Bode implements CupomDescontoBode {
    @Override
    public double calcularDesconto(double valorOriginal) {
        // Implemente a lógica específica para o cupom "Pague 3, Leve 4" aqui
        // Exemplo: a cada 3 produtos, 1 é grátis
        int quantidadeProdutos = (int) ((int) valorOriginal / ProdutoDaLoja.values()[0].getPreco()); // Supondo que o primeiro produto seja o mais barato
        double valorDesconto = quantidadeProdutos * ProdutoDaLoja.values()[0].getPreco();
        return valorOriginal - valorDesconto;
    }

    @Override
    public double calcularDesconto(double valorOriginal, List<ItemPedido> itens) {
        // O desconto específico baseado na quantidade de itens não se aplica a este cupom
        // Portanto, usaremos a lógica genérica de calcularDesconto que já implementamos acima
        return calcularDesconto(valorOriginal);
    }
}
