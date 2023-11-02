package bode.loja.pedido;

import bode.loja.cupom.CupomItemMaisBaratoGratis;
import bode.loja.cupom.CupomPague3ELeve4Bode;
import bode.loja.cupom.TipoDeCupom;
import bode.loja.excecoes.ExistemPedidosAbertosException;
import bode.loja.excecoes.NaoHaPedidosAPreparar;
import bode.loja.excecoes.PedidoNaoEstaPreparadoException;
import bode.loja.excecoes.PedidoNaoExisteException;
import bode.produtos.ProdutoDaLoja;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PedidoService {
    private List<Pedido> pedidos;
    private Queue<Pedido> filaPreparo;
    private int proximoNumeroPedido;
    private List<Pedido> pedidosEmAberto;
    private List<Pedido> pedidosCancelados;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.filaPreparo = new LinkedList<>();
        this.proximoNumeroPedido = 1;
        this.pedidosEmAberto = new ArrayList<>();
        this.pedidosCancelados = new ArrayList<>();

    }

    public int criaPedido(String nomeCliente) {
        Pedido novoPedido = new Pedido(nomeCliente);
        novoPedido.setNumeroPedido(proximoNumeroPedido);
        proximoNumeroPedido++;
        pedidos.add(novoPedido);
        filaPreparo.add(novoPedido);
        return novoPedido.getNumeroPedido();
    }


    public void adicionarItemAoPedido(int numeroPedido, int item) throws PedidoNaoExisteException {
        ProdutoDaLoja produto = ProdutoDaLoja.values()[item];
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            ItemPedido itemPedido = new ItemPedido(produto, 1);
            pedido.adicionarItem(itemPedido);
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public boolean removeItemDoPedido(int numeroPedido, int item) {
        ProdutoDaLoja produto = ProdutoDaLoja.values()[item];
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            List<ItemPedido> itens = pedido.getItens();
            for (ItemPedido itemPedido : itens) {
                if (itemPedido.getProduto() == produto) {
                    pedido.removerItem(itemPedido);
                    return true;
                }
            }
        }
        return false;
    }

    public List<TipoDeCupom> getCuponsElegiveis(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            List<TipoDeCupom> cuponsElegiveis = new ArrayList<>();
            int numeroItens = pedido.getItens().size();

            if (numeroItens >= 4) {
                cuponsElegiveis.add(TipoDeCupom.PAGUE3_LEVE4);
            }
            if (numeroItens >= 5) {
                cuponsElegiveis.add(TipoDeCupom.ITEM_MAIS_BARATO_GRATIS);
            }

            return cuponsElegiveis;
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public double aplicarCupom(int numeroPedido, TipoDeCupom tipoDeCupom) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            double valorOriginal = pedido.getValorTotal();
            double valorComDesconto = valorOriginal;

            if (tipoDeCupom == TipoDeCupom.PAGUE3_LEVE4) {
                CupomPague3ELeve4Bode cupom = new CupomPague3ELeve4Bode();
                valorComDesconto = cupom.calcularDesconto(valorOriginal);
            } else if (tipoDeCupom == TipoDeCupom.ITEM_MAIS_BARATO_GRATIS) {
                CupomItemMaisBaratoGratis cupom = new CupomItemMaisBaratoGratis();
                valorComDesconto = cupom.calcularDesconto(valorOriginal, pedido.getItens());
            }

            pedido.setValorTotal(valorComDesconto);
            return valorComDesconto;
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
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


    public void encerraVendas() throws ExistemPedidosAbertosException {
        boolean existemPedidosEmPreparo = false;

        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.EM_PREPARO) {
                existemPedidosEmPreparo = true;
                break;
            }
        }

        if (existemPedidosEmPreparo) {
            throw new ExistemPedidosAbertosException("A loja não pode ser fechada porque existem pedidos iniciados e não concluídos.");
        }

        System.out.println("Vendas fechadas.");
    }



    public int preparaProximoPedido() throws NaoHaPedidosAPreparar {
        Pedido proximoPedido = filaPreparo.poll();
        if (proximoPedido == null) {
            throw new NaoHaPedidosAPreparar();
        }
        proximoPedido.setStatus(StatusPedido.EM_PREPARO);
        return proximoPedido.getNumeroPedido();
    }

    public void entregarPedido(int numeroPedido) throws PedidoNaoExisteException, PedidoNaoEstaPreparadoException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            if (pedido.getStatus() != StatusPedido.EM_PREPARO) {
                throw new PedidoNaoEstaPreparadoException();
            }
            pedido.setStatus(StatusPedido.ENTREGUE);

            Pedido proximo = filaPreparo.poll();
            if (proximo != null) {
                proximo.setStatus(StatusPedido.EM_PREPARO);
                filaPreparo.add(proximo);
            }
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public Pedido getPedido(int numeroPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumeroPedido() == numeroPedido) {
                return pedido;
            }
        }
        return null;
    }

    public String getItensDoPedidoToString(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            List<ItemPedido> itens = pedido.getItens();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < itens.size(); i++) {
                ProdutoDaLoja produto = itens.get(i).getProduto();
                result.append(i).append(" - ").append(produto.name()).append(" - R$ ").append(produto.getPreco()).append("\n");
            }
            return result.toString();
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public double getValorAPagarDoPedido(int numeroPedido) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            return pedido.getValorTotal();
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public void adicionaCupom(int numeroPedido, TipoDeCupom tipoDeCupom) throws PedidoNaoExisteException {
        Pedido pedido = getPedido(numeroPedido);
        if (pedido != null) {
            double desconto = tipoDeCupom.getDesconto();
            pedido.aplicarCupom(desconto);
        } else {
            throw new PedidoNaoExisteException("Pedido com número " + numeroPedido + " não encontrado.");
        }
    }

    public String getEstatisticasDoDia() {
        double totalVendas = 0.0;
        int numeroTotalPedidosConcluidos = 0;

        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() == StatusPedido.FECHADO || pedido.getStatus() == StatusPedido.ENTREGUE) {
                numeroTotalPedidosConcluidos++;
                totalVendas += pedido.getValorTotal();
            }
        }

        StringBuilder estatisticas = new StringBuilder();
        estatisticas.append("Estatísticas de vendas do dia:\n");
        estatisticas.append("Total de vendas: R$ ").append(totalVendas).append("\n");
        estatisticas.append("Número de pedidos concluídos: ").append(numeroTotalPedidosConcluidos).append("\n");

        return estatisticas.toString();
    }

    public static String telaMenu() {
        String str = "___________________________________\n";
        for (ProdutoDaLoja produto : ProdutoDaLoja.values()) {
            str += produto.getCodigo() + " - ";
            str += produto.getDescricao() + " - R$ ";
            str += produto.getPreco() + "\n";
        }
        return str;
    }

    public void cancelarPedido(int numeroDoPedido) {
    }
}
