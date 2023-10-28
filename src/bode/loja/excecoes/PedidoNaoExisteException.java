package bode.loja.excecoes;

public class PedidoNaoExisteException extends Exception {
    public PedidoNaoExisteException(String s) {
        super("Pedido não existe.");
    }
}