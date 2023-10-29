package bode.loja;

import bode.loja.cliente.Fachada;
import bode.loja.cupom.TipoDeCupom;
import bode.loja.excecoes.ExistemPedidosAbertosException;
import bode.loja.excecoes.NaoHaPedidosAPreparar;
import bode.loja.excecoes.PedidoNaoEstaPreparadoException;
import bode.loja.excecoes.PedidoNaoExisteException;
import bode.loja.pedido.Pedido;
import bode.produtos.ProdutoDaLoja;

import java.util.List;
import java.util.Scanner;

public class LojaCampusIV {
    private static final int OPCAO_VOLTAR_TELA_MODO = 0;
    private static final int OPCAO_MODO_CLIENTE = 1;
    private static final int OPCAO_MODO_OPERACAO = 2;

    private static final int OPCAO_MODO_CLIENTE_FAZER_PEDIDO = 1;
    private static final int OPCAO_MODO_CLIENTE_ADICIONAR_ITEM_AO_PEDIDO = 2;
    private static final int OPCAO_MODO_CLIENTE_REMOVER_ITEM_DO_PEDIDO = 3;
    private static final int OPCAO_MODO_CLIENTE_FECHAR_PEDIDO = 4;
    private static final int OPCAO_MODO_CLIENTE_CANCELAR_PEDIDO = 5;

    private static final int OPCAO_MODO_OPERACAO_PREPARAR_PROXIMO_PEDIDO = 1;
    private static final int OPCAO_MODO_OPERACAO_VER_PEDIDOS_EM_PREPARO = 2;
    private static final int OPCAO_MODO_OPERACAO_VER_PEDIDOS_CANCELADOS = 3;
    private static final int OPCAO_MODO_OPERACAO_ENTREGAR_PEDIDO = 4;
    private static final int OPCAO_MODO_OPERACAO_ENCERRAR_VENDAS = 5;
    private static final int OPCAO_MODO_OPERACAO_ESTATISTICAS_VENDAS = 6;

    private static int OPCAO_SAIR = 0;

    public static void main(String[] args) throws NaoHaPedidosAPreparar {
        Fachada fachada = new Fachada();
        Scanner sc = new Scanner(System.in);

        System.out.println(telaInicial());

        int opcao = 0;
        do {
            System.out.println(telaModoDeOperacao());
            opcao = recebeInteiroNoIntervalo(sc, OPCAO_SAIR, OPCAO_MODO_OPERACAO);

            if( opcao == OPCAO_SAIR ) {
                System.out.println(mensagemDespedidaGeral());
                System.exit(0);
            }

            if( opcao == 1 ) entraMenuModoCliente(sc, fachada);
            else entraMenuModoOperacao(sc, fachada);

        } while(opcao != OPCAO_SAIR);
    }

    private static void entraMenuModoOperacao(Scanner sc, Fachada fachada) throws NaoHaPedidosAPreparar {

        int opcao;
        do {
            System.out.println(menuModoFuncionario());
            opcao = recebeInteiroNoIntervalo(sc, OPCAO_VOLTAR_TELA_MODO,
                    OPCAO_MODO_OPERACAO_ESTATISTICAS_VENDAS);
            switch (opcao) {
                case OPCAO_MODO_OPERACAO_PREPARAR_PROXIMO_PEDIDO:
                    menuPreparaProximoServico(fachada);
                    break;
                case OPCAO_MODO_OPERACAO_VER_PEDIDOS_EM_PREPARO:
                    exibirPedidosEmPreparo(fachada.getPedidosEmPreparo());
                    break;
                case OPCAO_MODO_OPERACAO_VER_PEDIDOS_CANCELADOS:
                    System.out.println(fachada.getPedidosCancelados());
                    break;
                case OPCAO_MODO_OPERACAO_ENTREGAR_PEDIDO:
                    menuEntregaPedido(sc, fachada);
                    break;
                case OPCAO_MODO_OPERACAO_ENCERRAR_VENDAS:
                    menuEncerraVendas(fachada);
                    break;
                case OPCAO_MODO_OPERACAO_ESTATISTICAS_VENDAS:
                    menuGraEstatisticas(fachada);
                    break;
            }
        } while(opcao != OPCAO_VOLTAR_TELA_MODO);
    }

    private static void exibirPedidosEmPreparo(List<Pedido> pedidosEmPreparo) {
        if (pedidosEmPreparo.isEmpty()) {
            System.out.println("Não há pedidos em preparo no momento.");
        } else {
            System.out.println("Pedidos em preparo:");
            for (Pedido pedido : pedidosEmPreparo) {
                System.out.println("Número do Pedido: " + pedido.getNumeroPedido());
            }
        }
    }
    private static int recebeInteiroNoIntervalo(Scanner sc, int menorValor, int maiorValor) {
        int numeroInteiro = 0;
        try {
            numeroInteiro = Integer.parseInt(sc.next());
        } catch(NumberFormatException nfe) {
            System.out.println("Digite um número no intervalo [" + menorValor + ", " + maiorValor + "]");
            return recebeInteiroNoIntervalo(sc, menorValor, maiorValor);
        }
        if(numeroInteiro < menorValor || numeroInteiro > maiorValor) {
            System.out.println("Digite um número no intervalo [" + menorValor + ", " + maiorValor + "]");
            return recebeInteiroNoIntervalo(sc, menorValor, maiorValor);
        }
        return numeroInteiro;
    }

    public static String telaMenu() {
        String str = "___________________________________\n";
        for (int i = 0; i < ProdutoDaLoja.values().length; i++) {
            str += (ProdutoDaLoja.values()[i].getCodigo() + " - ");
            str += (ProdutoDaLoja.values()[i].getDescricao() + " - R$ ");
            str += (ProdutoDaLoja.values()[i].getPreco() + "\n");
        }
        return str;
    }

    public static String telaInicial() {
        String str = "___________________________________\n";
        str += "Loja CAMPUS IV aberta. Bom dia!\n";
        return str;
    }

    public static String telaModoDeOperacao() {
        String str = "___________________________________\n";
        str += "Escolha o modo de operação:\n";
        str += "1 - modo cliente\n";
        str += "2 - modo operacao\n";
        str += "0 - para sair\n";
        return str;
    }

    private static void menuGraEstatisticas(Fachada fachada) {
        try {
            System.out.println(fachada.getEstatisticasDoDia());
        } catch (Exception e) {
            System.out.println("As vendas do dia não foram encerradas ainda.");
        }
    }

    private static void menuEncerraVendas(Fachada fachada) {
        try {
            fachada.encerraVendas();
        } catch (ExistemPedidosAbertosException e) {
            System.out.println("A loja não pode ser fechada porque existem pedidos iniciados e não concluídos.");
        }
    }

    private static void menuPreparaProximoServico(Fachada fachada) throws NaoHaPedidosAPreparar {
        try {
            int numeroPedido = fachada.preparaProximoPedido();
            System.out.println("Pedido " + numeroPedido + " está em preparo.");
        } catch (NaoHaPedidosAPreparar e) {
            System.out.println(e.getMessage());
        }
    }
    private static void menuEntregaPedido(Scanner sc, Fachada fachada) {
        System.out.println("Qual o número do pedido a ser entregue?");
        int numeroDoPedido = recebeInteiroNoIntervalo(sc, 0, Integer.MAX_VALUE);
        try {
            fachada.entregarPedido(numeroDoPedido);
            System.out.println("Pedido " + numeroDoPedido + " entregue com sucesso!");
        } catch (PedidoNaoEstaPreparadoException pnepe) {
            System.out.println("Pedido " + numeroDoPedido + " não está em preparo!");;
        } catch (PedidoNaoExisteException pnee) {
            System.out.println("Pedido " + numeroDoPedido + " não existe.");
        }
    }


    private static String menuModoFuncionario() {
        String str = "";
        str += (OPCAO_MODO_OPERACAO_PREPARAR_PROXIMO_PEDIDO + " - PARA PREPARAR PRÓXIMO PEDIDO DA FILA\n");
        str += (OPCAO_MODO_OPERACAO_VER_PEDIDOS_EM_PREPARO + " - PARA VER PEDIDOS EM PREPARO\n");
        str += (OPCAO_MODO_OPERACAO_VER_PEDIDOS_CANCELADOS + " - PARA VER PEDIDOS CANCELADOS\n");
        str += (OPCAO_MODO_OPERACAO_ENTREGAR_PEDIDO + " - PARA ENTREGAR PEDIDO\n");
        str += (OPCAO_MODO_OPERACAO_ENCERRAR_VENDAS + " - PARA ENCERRAR VENDAS\n");
        str += (OPCAO_MODO_OPERACAO_ESTATISTICAS_VENDAS + " - PARA VER ESTATÍSTICAS DE VENDAS\n");
        str += (OPCAO_VOLTAR_TELA_MODO + " - PARA VOLTAR PARA A TELA DE MODO DE OPERAÇÃO\n");

        return str;
    }

    private static int entraMenuModoCliente(Scanner sc, Fachada fachada) {
        int opcao = -1;
        System.out.println(menuModoCliente());
        opcao = recebeInteiroNoIntervalo(sc, OPCAO_VOLTAR_TELA_MODO, OPCAO_MODO_CLIENTE_FAZER_PEDIDO);
        if(opcao == OPCAO_VOLTAR_TELA_MODO)
            return opcao;
        int numeroDoPedido = -1;
        if(opcao == 1) {
            numeroDoPedido = criarPedido(sc, fachada);
        }


        do {
            System.out.println(telaParaMontarPedido(numeroDoPedido));
            opcao = recebeInteiroNoIntervalo(sc, OPCAO_MODO_CLIENTE_ADICIONAR_ITEM_AO_PEDIDO, OPCAO_MODO_CLIENTE_CANCELAR_PEDIDO);
            switch(opcao) {
                case OPCAO_MODO_CLIENTE_ADICIONAR_ITEM_AO_PEDIDO:
                    menuAdicionaItemAoPedido(sc, fachada, numeroDoPedido);
                    break;
                case OPCAO_MODO_CLIENTE_REMOVER_ITEM_DO_PEDIDO:
                    menuRemoveItemDoPedido(sc, fachada, numeroDoPedido);
                    break;
                case OPCAO_MODO_CLIENTE_FECHAR_PEDIDO:
                    menuFecharPedido(sc, fachada, numeroDoPedido);
                    opcao = OPCAO_VOLTAR_TELA_MODO;
                    break;
                case OPCAO_MODO_CLIENTE_CANCELAR_PEDIDO:
                    menuCancelarPedido(fachada, numeroDoPedido);
                    opcao = OPCAO_VOLTAR_TELA_MODO;
                    break;
            }
        } while(opcao != OPCAO_SAIR && opcao != OPCAO_VOLTAR_TELA_MODO);
        return opcao;
    }

    private static void menuCancelarPedido(Fachada fachada, int numeroDoPedido) {
        try {
            fachada.cancelarPedido(numeroDoPedido);
            System.out.println("Pedido " + numeroDoPedido + " cancelado!");
        } catch (PedidoNaoExisteException e) {
            System.out.println("Pedido " + numeroDoPedido + " não existe.");
        }
    }

    private static void menuRemoveItemDoPedido(Scanner sc, Fachada fachada, int numeroDoPedido) {
        try {
            removeItemDoPedido(sc, fachada, numeroDoPedido);
        } catch (PedidoNaoExisteException e) {
            throw new RuntimeException(e);
        }
    }


    private static void menuFecharPedido(Scanner sc, Fachada fachada, int numeroDoPedido) {
        System.out.println("Deseja adicionar algum cupom ao pedido?");
        System.out.println(" 0 - Sim \n 1 - Não");
        int resposta = recebeInteiroNoIntervalo(sc, 0, 1);
        String cupom = "";
        if(resposta == 0) {
            System.out.println("Informe o código do cupom");
            cupom = sc.next();
        }
        try {
            fechaPedido(fachada, numeroDoPedido, cupom);
        } catch (PedidoNaoExisteException e) {
            System.out.println("Pedido de código " + numeroDoPedido + " não existe.");
        }

    }

    private static void fechaPedido(Fachada fachada, int numeroDoPedido, String cupom) throws PedidoNaoExisteException {
        fachada.adicionaCupom(numeroDoPedido, validaCupom(cupom));
        System.out.println("Realizar pagamento agora - valor R$" + fachada.getValorAPagarDoPedido(numeroDoPedido));
        System.out.println(mensagemDespedidaModoCliente());
        fachada.fecharPedido(numeroDoPedido);
    }

    private static TipoDeCupom validaCupom(String cupom) {
        for(int i = 0; i < TipoDeCupom.values().length; i++) {
            if(cupom.toUpperCase().trim().equals(TipoDeCupom.values()[i].name()))
                return TipoDeCupom.values()[i];
        }
        return TipoDeCupom.values()[0];
    }

    private static void removeItemDoPedido(Scanner sc, Fachada fachada, int numeroDoPedido) throws PedidoNaoExisteException {
        System.out.println("Escolha o número do item a ser removido: ");
        System.out.println(fachada.getItensDoPedidoToString(numeroDoPedido));
        int item = recebeInteiroNoIntervalo(sc, 0, ProdutoDaLoja.values().length-1);
        if(fachada.removeItemDoPedido(numeroDoPedido, item) == false)
            System.out.println("Este item não está no pedido.");
        else System.out.println("Item " + ProdutoDaLoja.values()[item].name() + " removido.");
    }

    private static void menuAdicionaItemAoPedido(Scanner sc, Fachada fachada, int numeroDoPedido) {
        System.out.println(telaMenu());
        System.out.println("Escolha um item do cardápio para adicionar ao pedido " + numeroDoPedido + ".");
        int item = recebeInteiroNoIntervalo(sc, 0, ProdutoDaLoja.values().length-1);
        try {
            fachada.adicionarItemAoPedido(numeroDoPedido, item);
        } catch (PedidoNaoExisteException e) {
            System.out.println("Pedido " + numeroDoPedido + " não existe.");
        }
        System.out.println("Item adicionado: " + ProdutoDaLoja.values()[item].name() + " R$ " +
                ProdutoDaLoja.values()[item].getPreco());
    }

    private static String telaParaMontarPedido(int pedido) {
        String str = "___________________________________\n";
        str += (OPCAO_MODO_CLIENTE_ADICIONAR_ITEM_AO_PEDIDO + " - PARA ADICIONAR ITEM AO PEDIDO " + pedido + ".\n");
        str += (OPCAO_MODO_CLIENTE_REMOVER_ITEM_DO_PEDIDO + " - PARA REMOVER ITEM DO PEDIDO " + pedido + ".\n");
        str += (OPCAO_MODO_CLIENTE_FECHAR_PEDIDO + " - PARA FECHAR O PEDIDO " + pedido + ".\n");
        str += (OPCAO_MODO_CLIENTE_CANCELAR_PEDIDO + " - PARA CANCELAR O PEDIDO " + pedido + ".\n");
        return str;
    }

    private static int criarPedido(Scanner sc, Fachada fachada) {
        System.out.println("Você está iniciando um novo pedido. Informe seu nome para continuar.\n");
        String nome = sc.next();
        int pedido = fachada.criaPedido(nome);
        System.out.println("Pedido de numero " + pedido + " foi criado. Agora monte seu pedido.\n");
        return pedido;
    }

    private static String menuModoCliente() {
        String str = "___________________________________\n";
        str += (OPCAO_MODO_CLIENTE_FAZER_PEDIDO + " - FAZER PEDIDO\n");
        str += (OPCAO_VOLTAR_TELA_MODO + " - VOLTAR PARA ESCOLHER MODO DE USO\n");
        return str;
    }

    private static String telaModoCliente() {
        String str = "___________________________________\n";
        str += "Escolha:\n";
        str += (OPCAO_MODO_CLIENTE + " - modo cliente\n");
        str += (OPCAO_MODO_OPERACAO + " - modo operacao\n");
        return str;
    }

    private static String mensagemDespedidaGeral() {
        String str = "___________________________________\n";
        str += "Obrigada por usar nosso app do BODE\n";
        return str;
    }

    private static String mensagemDespedidaModoCliente() {
        String str = "___________________________________\n";
        str += "Pedido realizado\n";
        str += "Pegue seu pedido no balcao - será chamado pelo nome\n";
        return str;
    }
}