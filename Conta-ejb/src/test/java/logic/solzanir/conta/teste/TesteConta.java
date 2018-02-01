package logic.solzanir.conta.teste;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import logic.solzanir.banco.models.BancoVO;
import logic.solzanir.conta.excecoes.ContaException;
import logic.solzanir.conta.modelos.ContaVO;
import logic.solzanir.conta.beans.ContaBean;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.modelos.ContaDTO;
import logic.solzanir.conta.modelos.ContaVencimentoVO;
import logic.solzanir.conta.modelos.TipoLancamentoDTO;
import logic.solzanir.conta.util.Constantes;
import logic.solzanir.conta.util.TipoLancamentoEnum;
import logic.solzanir.conta.util.ValidadorData;
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

    private ContaVO conta;
    
    private final String dataConta = "1900-01-01";

    @Deployment
    public static JavaArchive criarArquivoTeste() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ContaBean.class,
                        ContaDAO.class,
                        ValidadorData.class,
                        ContaException.class,
                        ContaVO.class,
                        ContaDTO.class,
                        TipoLancamentoDTO.class,
                        ContaVencimentoVO.class,
                        TipoLancamentoEnum.class,
                        Constantes.class,
                        BancoVO.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public TesteConta() {
        this.conta = new ContaVO();
        conta.setNome("TESTE");
        conta.setData(dataConta);
        conta.setValor(999);
        conta.setTipoLancamento(1);
    }

    @Test
    @InSequence(1)
    public void testaInsercaoDeConta() {

        System.out.println("[TESTANDO INSERCAO DE CONTA]");

        try {

            conta = bean.insereConta(conta);
            Assert.assertTrue(conta.getId() > 0);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(2)
    public void testaAtualizacaoDeConta() {

        System.out.println("[TESTANDO ATUALIZACAO DE CONTA]");

        try {

            String nomeConta = "CONTA ATUALIZADA";
            conta = bean.insereConta(conta);
            conta.setNome(nomeConta);
            bean.atualizaConta(conta);
            ContaVO contaRetorno = bean.buscaContaPorID(conta.getId());
            Assert.assertTrue(contaRetorno.getNome().equals(nomeConta));
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(3)
    public void testaRemocaoDeConta() {

        System.out.println("[TESTANDO REMOCAO DE CONTA]");

        try {

            conta = bean.insereConta(conta);
            Assert.assertTrue(conta.getId() > 0);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(4)
    public void testaConsultaDeContas() {

        System.out.println("[TESTANDO CONSULTA DE CONTAS]");

        try {

            List<ContaVO> contas = new ArrayList<>();
            
            conta = bean.insereConta(conta);
            contas = bean.buscaTodasContas();
            Assert.assertTrue(contas.size() > 0);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(5)
    public void testaConsultaDeContaPorNome() throws ContaException {

        System.out.println("[TESTANDO CONSULTA DE CONTAS POR NOME]");

        try {

            List<ContaVO> contas = new ArrayList<>();
            
            conta = bean.insereConta(conta);
            contas = bean.buscaContaPorNome(conta);
            
            boolean valida = false;
            for(ContaVO c : contas){
                if(c.getId().equals(conta.getId())){
                    valida = true;
                }
            }
            
            Assert.assertTrue(valida);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(6)
    public void testaConsultaDeContaPorID() {

        System.out.println("[TESTANDO CONSULTA DE CONTA POR ID]");
        
        try {

            conta = bean.insereConta(conta);
            ContaVO contaRetorno = bean.buscaContaPorID(conta.getId());
            Assert.assertTrue(contaRetorno.getNome().equals(conta.getNome()));
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(7)
    public void testaConsultaDeContaPorTipoLancamento() {

        System.out.println("[TESTANDO CONSULTA DE CONTAS POR TIPO DE LANCAMENTO]");
        
        try {

            List<ContaVO> contas = new ArrayList<>();
            
            conta = bean.insereConta(conta);
            contas = bean.buscaContaPorTipoLancamento(conta);
            
            boolean valida = false;
            for(ContaVO c : contas){
                if(c.getId().equals(conta.getId())){
                    valida = true;
                }
            }
            
            Assert.assertTrue(valida);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(8)
    public void testaConsultaDeContaPorVencimento() {

        System.out.println("[TESTANDO CONSULTA DE CONTAS POR DATA DE VENCIMENTO]");
        
        try {

            List<ContaVO> contas = new ArrayList<>();
            
            conta = bean.insereConta(conta);
            ContaVencimentoVO contaVencimento = new ContaVencimentoVO();
            contaVencimento.setVencimentoFinal(dataConta);
            contas = bean.buscaContaPorData(contaVencimento);
            
            boolean valida = false;
            for(ContaVO c : contas){
                if(c.getId().equals(conta.getId())){
                    valida = true;
                }
            }
            
            Assert.assertTrue(valida);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

    @Test
    @InSequence(9)
    public void testaConsultaDeContaPorAnoMes() {

        System.out.println("[TESTANDO CONSULTA DE CONTAS POR ANO/MES]");
        
        try {

            List<ContaVO> contas = new ArrayList<>();
            
            conta = bean.insereConta(conta);
            ContaVencimentoVO contaVencimento = new ContaVencimentoVO();
            contaVencimento.setAno(1900);
            contas = bean.buscaContaPorData(contaVencimento);
            
            boolean valida = false;
            for(ContaVO c : contas){
                if(c.getId().equals(conta.getId())){
                    valida = true;
                }
            }
            
            Assert.assertTrue(valida);
            bean.removeConta(conta);

        } catch (ContaException ex) {
            System.err.println("[ERRO]: " + ex.getMensagem());
        }

    }

}
