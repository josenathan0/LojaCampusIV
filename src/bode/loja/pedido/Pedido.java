package bode.loja.pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private String nomeCliente;
    private Date dataHoraCriacao;
    private List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido status;
    private int numeroPedido;

    public Pedido(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        this.dataHoraCriacao = new Date();
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
        this.status = StatusPedido.AGUARDANDO_ITENS;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public Date getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void adicionarItem(ItemPedido item) {
        if (status == StatusPedido.AGUARDANDO_ITENS) {
            itens.add(item);
            calcularValorTotal();
        }
    }

    public void removerItem(ItemPedido item) {
        if (status == StatusPedido.AGUARDANDO_ITENS) {
            itens.remove(item);
            calcularValorTotal();
        }
    }

    public double aplicarCupom(double desconto) {
        if (status == StatusPedido.AGUARDANDO_ITENS) {
            double descontoAplicado = valorTotal * desconto;
            valorTotal -= descontoAplicado;
            return descontoAplicado;
        }
        return 0;
    }

    public double calcularValorTotal() {
        if (status == StatusPedido.AGUARDANDO_ITENS) {
            valorTotal = 0.0;
            for (ItemPedido item : itens) {
                valorTotal += item.getSubtotal();
            }
        }
        return valorTotal;
    }
    public static List<Pedido> getPedidosEmAberto(List<Pedido> pedidos) {
        List<Pedido> pedidosEmAberto = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.AGUARDANDO_ITENS) {
                pedidosEmAberto.add(pedido);
            }
        }
        return pedidosEmAberto;
    }

    public static List<Pedido> getPedidosCancelados(List<Pedido> pedidos) {
        List<Pedido> pedidosCancelados = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.CANCELADO) {
                pedidosCancelados.add(pedido);
            }
        }
        return pedidosCancelados;
    }
    public void fechar() {
        status = StatusPedido.FECHADO;
    }

    public void cancelar() {
        status = StatusPedido.CANCELADO;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setValorTotal(double valorComDesconto) {
    }
}
