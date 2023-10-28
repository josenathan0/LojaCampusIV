package bode.loja.excecoes;

public class ExistemPedidosAbertosException extends Exception {
    public ExistemPedidosAbertosException(String s) {
        super("Existem pedidos abertos. Encerre todas as vendas primeiro.");
    }
}
