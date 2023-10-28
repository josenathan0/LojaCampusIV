package bode.loja.excecoes;
public class PedidoNaoEstaPreparadoException extends Exception {
    public PedidoNaoEstaPreparadoException() {
        super("O pedido não está em preparo.");
    }
}
