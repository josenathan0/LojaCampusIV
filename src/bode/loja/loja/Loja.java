package bode.loja.loja;

import bode.loja.cupom.TipoDeCupom;
import bode.loja.excecoes.PedidoNaoExisteException;
import bode.loja.pedido.*;
import bode.produtos.ProdutoDaLoja;

public class Loja {
    private FachadaPedido fachadaPedidos;

    public Loja() {
        this.fachadaPedidos = new FachadaPedido();
    }

    public int criarPedido(String nomeCliente) {
        return fachadaPedidos.criarPedido(nomeCliente);
    }

    public void adicionarItemAoPedido(int numeroPedido, ProdutoDaLoja produto, int quantidade) throws PedidoNaoExisteException {
        fachadaPedidos.adicionarItemAoPedido(numeroPedido, produto, quantidade);
    }

    public void removerItemDoPedido(int numeroPedido, ProdutoDaLoja produto, int quantidade) throws PedidoNaoExisteException {
        fachadaPedidos.removerItemDoPedido(numeroPedido, produto, quantidade);
    }

    public void aplicarCupom(int numeroPedido, TipoDeCupom tipoDeCupom) throws PedidoNaoExisteException {
        fachadaPedidos.aplicarCupom(numeroPedido, tipoDeCupom);
    }

    public double calcularValorTotal(int numeroPedido) throws PedidoNaoExisteException {
        return fachadaPedidos.calcularValorTotal(numeroPedido);
    }

    public void fecharPedido(int numeroPedido) throws PedidoNaoExisteException {
        fachadaPedidos.fecharPedido(numeroPedido);
    }

    public void cancelarPedido(int numeroPedido) throws PedidoNaoExisteException {
        fachadaPedidos.cancelarPedido(numeroPedido);
    }

    public Pedido getPedido(int numeroPedido) throws PedidoNaoExisteException {
        return fachadaPedidos.getPedido(numeroPedido);
    }

    public void encerrarVendas() {
        fachadaPedidos.encerraVendas();
    }

    public void exibirPedidosEmPreparo() {
        System.out.println("Pedidos em preparo:");
        for (Pedido pedido : fachadaPedidos.getPedidosEmPreparo()) {
            System.out.println(pedido);
        }
    }

    public void exibirPedidosCancelados() {
        System.out.println("Pedidos cancelados:");
        for (Pedido pedido : fachadaPedidos.getPedidosCancelados()) {
            System.out.println(pedido);
        }
    }
}
