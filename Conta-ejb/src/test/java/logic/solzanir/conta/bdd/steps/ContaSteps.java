package logic.solzanir.conta.bdd.steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import logic.solzanir.agendador.Agendador;
import logic.solzanir.banco.beans.BancoBean;
import logic.solzanir.banco.database.BancoDAO;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.banco.models.Banco;
import logic.solzanir.conta.beans.ContaBean;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.models.Conta;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.models.TipoLancamento;
import logic.solzanir.conta.util.Constantes;
import logic.solzanir.message.producer.ProducerBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ContaSteps {

    private Conta contaTest;
    private String mensagemExcecao;
    private Double valorSaque;

    //TODO precisamos injetar essa classe
    private ContaDAO dao = new ContaDAO();

    @Deployment
    public static JavaArchive criarArquivoTeste() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ContaBean.class,
                        ContaDAO.class,
                        Conta.class,
                        TipoLancamento.class,
                        ContaVencimento.class,
                        Constantes.class,
                        Banco.class,
                        BancoBean.class,
                        BancoDAO.class,
                        GestaoBanco.class,
                        ProducerBean.class,
                        Agendador.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Dado("^que a conta corrente \"(.*?)\" possua saldo positivo de \"(.*?)\"$")
    public void queAContaCorrentePossuaSaldoPositivoDe(String nomeConta, Double saldo) {
        dao.criarConta(1, nomeConta, saldo);
    }

    @Quando("^seleciona a conta \"(.*?)\"$")
    public void selecionaAConta(String nomeConta) {
        contaTest = dao.getContaDB(nomeConta);
        assertNotNull(contaTest);
    }

    @Quando("^informado o valor \"(.*?)\" para saque da conta$")
    public void informadoOValorParaSaqueDaConta(final Double valorSaque) {
        contaTest.setValor(contaTest.getValor() - valorSaque);
    }

    @Quando("^confirmada a transacao$")
    public void confirmadaATransacao() {
        try {
            dao.atualizarSaldo(contaTest.getValor());

        } catch (Exception ex) {
            this.mensagemExcecao = ex.getMessage();
        }
    }

    @Entao("^o saldo da conta e atualizado para \"(.*?)\"$")
    public void oSaldoDaContaEAtualizadoPara(Double saldoFinal) {
        assertEquals(saldoFinal, contaTest.getValor());
    }

    @Entao("^se apresenta a mensagem \"(.*?)\"$")
    public void seApresentaAMensagem(String mensagem) {
        assertEquals(this.mensagemExcecao, mensagem);
    }
}
