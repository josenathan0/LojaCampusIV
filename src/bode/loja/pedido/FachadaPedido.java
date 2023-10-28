package bode.loja.pedido;

import bode.loja.cupom.*;
import bode.loja.excecoes.PedidoNaoExisteException;
import bode.produtos.ProdutoDaLoja;

import java.util.ArrayList;
import java.util.List;

public class FachadaPedido {
    private List<Pedido> pedidos;
    private int proximoNumeroPedido;

    public FachadaPedido() {
        pedidos = new ArrayList<>();
        proximoNumeroPedido = 1;
    }

    public int criarPedido(String nomeCliente) {
        Pedido pedido = new Pedido(nomeCliente);
        pedido.setStatus(StatusPedido.AGUARDANDO_ITENS);
        pedido.setNumeroPedido(proximoNumeroPedido);
        pedidos.add(pedido);
        return proximoNumeroPedido++;
    }

    public Pedido getPedido(int numeroPedido) throws PedidoNaoExisteException {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumeroPedido() == numeroPedido) {
                return pedido;
            }
        }
        throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
    }

    public void adicionarItemAoPedido(int numeroPedido, ProdutoDaLoja produto, int quantidade) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.adicionarItem(item);
    }

    public void removerItemDoPedido(int numeroPedido, ProdutoDaLoja produto, int quantidade) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.removerItem(item);
    }

    public void aplicarCupom(int numeroPedido, TipoDeCupom tipoDeCupom) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        CupomDescontoBode cupom = obterCupomPeloTipo(tipoDeCupom);
        double desconto = cupom.calcularDesconto(pedido.calcularValorTotal());
        pedido.aplicarCupom(desconto);
    }

    public double calcularValorTotal(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        return pedido.calcularValorTotal();
    }

    private CupomDescontoBode obterCupomPeloTipo(TipoDeCupom tipoDeCupom) {
        switch (tipoDeCupom) {
            case PAGUE3_LEVE4:
                return new CupomPague3ELeve4Bode();
            case ITEM_MAIS_BARATO_GRATIS:
                return new CupomItemMaisBaratoGratis();
            case SEM_DESCONTO:
            default:
                return new CupomSemDesconto();
        }
    }

    public void fecharPedido(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        pedido.fechar();
    }

    public void cancelarPedido(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        pedido.cancelar();
    }

    public List<Pedido> getPedidosEmPreparo() {
        List<Pedido> pedidosEmPreparo = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.EM_PREPARO) {
                pedidosEmPreparo.add(pedido);
            }
        }
        return pedidosEmPreparo;
    }

    public List<Pedido> getPedidosCancelados() {
        List<Pedido> pedidosCancelados = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.CANCELADO) {
                pedidosCancelados.add(pedido);
            }
        }
        return pedidosCancelados;
    }

    public void encerraVendas() {
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() != StatusPedido.FINALIZADO && pedido.getStatus() != StatusPedido.CANCELADO) {
                return; // Não é possível encerrar as vendas enquanto houver pedidos não finalizados ou cancelados.
            }
        }
        // Todas as vendas foram finalizadas ou canceladas.
        pedidos.clear(); // Limpa a lista de pedidos.
        proximoNumeroPedido = 1; // Reseta o número do próximo pedido.
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
