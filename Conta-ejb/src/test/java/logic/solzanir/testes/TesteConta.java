package logic.solzanir.testes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import logic.solzanir.excecoes.ContaException;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.beans.ContaBean;
import logic.solzanir.modelos.ContaVencimentoVO;
import logic.solzanir.util.Constantes;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 07/01/2018
 */

public class TesteConta {
    /*
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
    */
}
