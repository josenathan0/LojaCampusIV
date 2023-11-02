package bode.loja.cliente;

import bode.loja.pedido.PedidoService;
import bode.loja.cupom.TipoDeCupom;
import bode.loja.excecoes.ExistemPedidosAbertosException;
import bode.loja.excecoes.NaoHaPedidosAPreparar;
import bode.loja.excecoes.PedidoNaoEstaPreparadoException;
import bode.loja.excecoes.PedidoNaoExisteException;
import bode.loja.pedido.Pedido;

import java.util.List;

public class Fachada {
    private PedidoService pedidoService;

    public Fachada() {
        this.pedidoService = new PedidoService();
    }
    public List<TipoDeCupom> getCuponsElegiveis(int numeroPedido) throws PedidoNaoExisteException {
        return pedidoService.getCuponsElegiveis(numeroPedido);
    }

    public double aplicarCupom(int numeroPedido, TipoDeCupom tipoDeCupom) throws PedidoNaoExisteException {
        return pedidoService.aplicarCupom(numeroPedido, tipoDeCupom);
    }

    public List<Pedido> getPedidosEmPreparo() {
        return pedidoService.getPedidosEmPreparo();
    }

    public List<Pedido> getPedidosCancelados() {
        return pedidoService.getPedidosCancelados();
    }
    public String getEstatisticasDoDia() {
        return pedidoService.getEstatisticasDoDia();
    }

    public void encerraVendas() throws ExistemPedidosAbertosException {
        pedidoService.encerraVendas();
    }

    public int preparaProximoPedido() throws NaoHaPedidosAPreparar {
        return pedidoService.preparaProximoPedido();
    }

    public void entregarPedido(int numeroDoPedido) throws PedidoNaoEstaPreparadoException, PedidoNaoExisteException {
        pedidoService.entregarPedido(numeroDoPedido);
    }

    public void cancelarPedido(int numeroDoPedido) throws PedidoNaoExisteException {
        pedidoService.cancelarPedido();
    }

    public void adicionaCupom(int numeroDoPedido, TipoDeCupom validaCupom) throws PedidoNaoExisteException {
        pedidoService.adicionaCupom(numeroDoPedido, validaCupom);
    }

    public double getValorAPagarDoPedido(int numeroDoPedido) throws PedidoNaoExisteException {
        return pedidoService.getValorAPagarDoPedido(numeroDoPedido);
    }

    public void fecharPedido(int numeroDoPedido) throws PedidoNaoExisteException {
        pedidoService.cancelarPedido();
    }

    public String getItensDoPedidoToString(int numeroDoPedido) throws PedidoNaoExisteException {
        return pedidoService.getItensDoPedidoToString(numeroDoPedido);
    }

    public void adicionarItemAoPedido(int numeroDoPedido, int item) throws PedidoNaoExisteException {
        pedidoService.adicionarItemAoPedido(numeroDoPedido, item);
    }

    public boolean removeItemDoPedido(int numeroDoPedido, int item) {
        return pedidoService.removeItemDoPedido(numeroDoPedido, item);
    }

    public int criaPedido(String nome) {
        return pedidoService.criaPedido(nome);
    }
}
