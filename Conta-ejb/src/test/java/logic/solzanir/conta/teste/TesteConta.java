package logic.solzanir.conta.teste;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import logic.solzanir.banco.beans.BancoBean;
import logic.solzanir.banco.database.BancoDAO;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.banco.models.Banco;
import logic.solzanir.conta.exception.ContaException;
import logic.solzanir.conta.beans.ContaBean;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.models.Conta;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.models.TipoLancamento;
import logic.solzanir.conta.util.Constantes;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 07/01/2018
 */
@RunWith(Arquillian.class)
public class TesteConta {

    @Inject
    private ContaBean bean;

    @Inject
    private GestaoBanco gestao;

    private Conta conta;

    private final Date dataConta = new Date(System.currentTimeMillis());

    @Deployment
    public static JavaArchive criarArquivoTeste() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ContaBean.class,
                        ContaDAO.class,
                        ContaException.class,
                        Conta.class,
                        TipoLancamento.class,
                        ContaVencimento.class,
                        Constantes.class,
                        Banco.class,
                        BancoBean.class,
                        BancoDAO.class,
                        GestaoBanco.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public TesteConta() {

        TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(3);

        conta = new Conta();
        conta.setNome("TESTE");
        conta.setData(dataConta);
        conta.setValor(999.99);
        conta.setTipoLancamento(tipoLancamento);

    }

    @Test
    @InSequence(1)
    public void testarInsercaoDeContaNova() {

        try {

            conta = bean.insertConta(conta);

            bean.removeConta(conta);
            Assert.assertTrue(conta.getId() > 0);

        } catch (ContaException ex) {
            //Implementar LOGGER
        }

    }

    @Test
    @InSequence(2)
    public void testarAtualizacaoDeConta() throws ContaException {

        String nomeConta = "CONTA ATUALIZADA";
        conta = bean.insertConta(conta);
        conta.setNome(nomeConta);
        bean.updateConta(conta);
        Conta contaRetorno = bean.findContaById(conta.getId());

        bean.removeConta(conta);
        Assert.assertTrue(contaRetorno.getNome().equals(nomeConta));

    }

    @Test
    @InSequence(3)
    public void testaRemocaoDeConta() throws ContaException {

        conta = bean.insertConta(conta);

        bean.removeConta(conta);
        Assert.assertTrue(conta.getId() > 0);

    }

    @Test
    @InSequence(4)
    public void testaConsultaDeContas() throws ContaException {

        List<Conta> contas = new ArrayList<>();
        conta = bean.insertConta(conta);
        contas = bean.findAllConta();

        bean.removeConta(conta);
        Assert.assertTrue(contas.size() > 0);

    }

    @Test
    @InSequence(5)
    public void testaConsultaDeContaPorNome() throws ContaException {

        List<Conta> contas = new ArrayList<>();
        conta = bean.insertConta(conta);
        contas = bean.findContaByName(conta);

        boolean valida = false;
        for (Conta c : contas) {
            if (c.getId().equals(conta.getId())) {
                valida = true;
            }
        }

        bean.removeConta(conta);
        Assert.assertTrue(valida);

    }

    @Test
    @InSequence(6)
    public void testaConsultaDeContaPorID() throws ContaException {

        conta = bean.insertConta(conta);
        Conta contaRetorno = bean.findContaById(conta.getId());

        bean.removeConta(conta);
        Assert.assertTrue(contaRetorno.getNome().equals(conta.getNome()));

    }

    @Test
    @InSequence(7)
    public void testaConsultaDeContaPorTipoLancamento() throws ContaException {

        List<Conta> contas = new ArrayList<>();
        conta = bean.insertConta(conta);
        contas = bean.findContaByTipoLancamento(conta);
        boolean valida = false;

        for (Conta c : contas) {
            if (c.getId().equals(conta.getId())) {
                valida = true;
            }
        }

        bean.removeConta(conta);
        Assert.assertTrue(valida);

    }

    @Test
    @InSequence(8)
    public void testaConsultaDeContaPorVencimento() throws ContaException {

        List<Conta> contas = new ArrayList<>();
        conta = bean.insertConta(conta);
        ContaVencimento contaVencimento = new ContaVencimento();
        contaVencimento.setVencimentoFinal(dataConta);
        contas = bean.findContaByData(contaVencimento);

        boolean valida = false;
        for (Conta c : contas) {
            if (c.getId().equals(conta.getId())) {
                valida = true;
            }
        }

        bean.removeConta(conta);
        Assert.assertTrue(valida);

    }

    @Test
    @InSequence(9)
    public void testaConsultaDeContaPorAnoMes() throws ContaException {

        List<Conta> contas = new ArrayList<>();
        conta = bean.insertConta(conta);
        ContaVencimento contaVencimento = new ContaVencimento();
        contaVencimento.setAno(2018);
        contas = bean.findContaByData(contaVencimento);

        boolean valida = false;
        for (Conta c : contas) {
            if (c.getId().equals(conta.getId())) {
                valida = true;
            }
        }

        bean.removeConta(conta);
        Assert.assertTrue(valida);

    }

    @Test
    @InSequence(10)
    public void testaLogMovimentacaoContaCorrente() throws ContaException, InterruptedException {

        TipoLancamento lancamento = new TipoLancamento();
        lancamento.setId(4);

        conta.setNome("TESTE BANCO");
        conta.setTipoLancamento(lancamento);
        bean.insertConta(conta);

        sleep(500);

        List<Conta> banco = gestao.getBanco();

        boolean valida = false;
        for (Conta c : banco) {
            if (c.getNome().equals("TESTE BANCO")) {
                valida = true;
            }
        }

        gestao.clearBanco();
        bean.removeConta(conta);
        Assert.assertTrue(valida);

    }

    @Test
    @InSequence(11)
    public void testaLogMovimentacaoContaCorrenteComMaisDeUmaConta() throws ContaException, InterruptedException {

        TipoLancamento lancamento = new TipoLancamento();
        lancamento.setId(4);

        //Conta 1
        conta.setNome("TESTE BANCO");
        conta.setTipoLancamento(lancamento);

        bean.insertConta(conta);

        //Conta 2
        Conta conta2 = new Conta();
        conta2.setNome("TESTE BANCO 2");
        conta2.setData(new Date(System.currentTimeMillis()));
        conta2.setValor(888);
        conta2.setTipoLancamento(lancamento);

        bean.insertConta(conta2);

        sleep(500);

        List<Conta> banco = gestao.getBanco();

        Assert.assertEquals(2, banco.size());

        gestao.clearBanco();
        bean.removeConta(conta);
        bean.removeConta(conta2);

    }

}
