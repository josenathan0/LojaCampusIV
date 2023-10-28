package bode.loja.excecoes;

public class NaoHaPedidosAPreparar extends Exception {
    public NaoHaPedidosAPreparar() {
        super("Não há pedidos para preparar.");
    }
}
