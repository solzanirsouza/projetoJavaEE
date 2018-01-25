package logic.solzanir.testes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import logic.solzanir.excecoes.ContaException;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.beans.ContaBean;
import logic.solzanir.database.ContaDAO;
import logic.solzanir.modelos.ContaVencimentoVO;
import logic.solzanir.util.Constantes;
import logic.solzanir.util.TipoLancamentoEnum;
import logic.solzanir.util.ValidadorData;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 07/01/2018
 */
@RunWith(Arquillian.class)
public class TesteConta {

    @Deployment
    public static Archive<?> criarArquivoTeste() {
        Archive<?> arquivoTeste = ShrinkWrap.create(WebArchive.class, "aplicacaoTeste.jar")
                .addClasses(ContaBean.class, 
                            ContaDAO.class, 
                            ValidadorData.class, 
                            ContaException.class,
                            ContaVO.class,
                            ContaVencimentoVO.class,
                            TipoLancamentoEnum.class,
                            Constantes.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return arquivoTeste;
    }

    @Inject
    private ContaBean bean;
    
    ContaVO conta = new ContaVO();

    @Before
    public void criaContaTeste() {
        
        conta.setNome("TESTE");
        conta.setData("1900-01-01");
        conta.setValor(999);
        conta.setTipoLancamento("BOLETO");

    }

    @Test
    @InSequence(1)
    public void testaInsercaoDeConta() {
      
        try {
            conta = bean.insereConta(conta);
            Assert.assertEquals("CONTA TESTE", conta.getNome());
            Assert.assertTrue(conta.getId() > Constantes.ValorInteiroInicial);
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }
    
    @Test
    @InSequence(2)
    public void testaConsultaDeContas() {
        
        try {
            List<ContaVO> contas = bean.buscaTodasContas();
            Assert.assertTrue(contas.contains(conta));
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }

    @Test
    @InSequence(3)
    public void testaConsultaDeContaPorNome() throws ContaException {
       
        try {
            List<ContaVO> contas = bean.buscaContaPorNome(conta);
            Assert.assertTrue(contas.size() > 0);
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }

    @Test
    @InSequence(4)
    public void testaAtualizacaoDeConta_testaBuscaPorID() {
        
        try {
            
            String nomeAtualizadoContaTeste = "CONTA TESTE ATUALIZADA";
            conta.setNome(nomeAtualizadoContaTeste);
            bean.atualizaConta(conta);
            conta = bean.buscaContaPorID(conta.getId());
            Assert.assertEquals("CONTA TESTE ATUALIZADA", conta.getNome());
            
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }
    
    @Test
    @InSequence(5)
    public void testaConsultaDeContaPorVencimento(){
        
        ContaVencimentoVO vencimento = new ContaVencimentoVO();
        vencimento.setVencimentoInicial("1900-01-01");
        vencimento.setVencimentoFinal("1900-01-31");
        
        try {
            List<ContaVO> contas = bean.buscaContaPorData(vencimento);
            Assert.assertTrue(contas.contains(conta));
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }
    
    @Test
    @InSequence(6)
    public void testaConsultaDeContaPorAnoMes(){
        
        ContaVencimentoVO vencimento = new ContaVencimentoVO();
        List<ContaVO> contas = new ArrayList<>();
        
        try {
            
            vencimento.setAno(1900);
            contas = bean.buscaContaPorData(vencimento);
            Assert.assertTrue(contas.contains(conta));
            
            contas.clear();
            vencimento.setMes(1);
            contas = bean.buscaContaPorData(vencimento);
            Assert.assertTrue(contas.contains(conta));
            
        } catch (ContaException ex) {
            System.err.println("[ERRO TESTE CONTA]: " + ex.getMensagem());
        } catch (SQLException ex) {
            System.err.println("[ERRO TESTE DATABASE]: " + ex);
        }
        
    }

}
