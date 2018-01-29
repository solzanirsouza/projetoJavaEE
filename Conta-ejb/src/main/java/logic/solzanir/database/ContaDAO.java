package logic.solzanir.database;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import logic.solzanir.modelos.ContaDTO;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.modelos.ContaVencimentoVO;
import logic.solzanir.modelos.TipoLancamentoDTO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Stateless
public class ContaDAO {
    
    @PersistenceContext
    EntityManager manager;

    //Insere uma nova conta na base de dados
    public ContaDTO insertConta(ContaDTO conta) {
        
        manager.persist(conta);
        return conta;
        
    }

    //Atualiza conta na base de dados
    public void updateConta(ContaDTO conta) {
        
        ContaDTO dto = manager.find(ContaDTO.class, conta.getId());
        manager.merge(dto);
        
    }

    //apaga conta da base de dados
    public void deleteConta(ContaVO conta) {
        
        ContaDTO dto = manager.find(ContaDTO.class, conta.getId());
        manager.remove(dto);
        
    }

    //Buscar todas as contas na base de dados
    public List<ContaDTO> getContas() {
        
        String sql = "select c from conta c";
        Query query = manager.createQuery(sql);
        return query.getResultList();
        
    }

    //Buscar conta por ID na base de dados
    public ContaDTO getConta(int id) {
        
        return manager.find(ContaDTO.class, id);
        
    }

    //busca conta na base de dados pelo nome (ou parte dele)
    //A consulta não é case sensitive, porém deve-se respeitar a acentuacao
    public List<ContaDTO> getContaPorNome(ContaDTO conta) {

        String sql = "select c from conta c where upper(c.nome) like '%" + conta.getNome().toUpperCase() + "%'";
        Query query = manager.createQuery(sql);
        return query.getResultList();

    }

    //Buscando Contas por periodo de vencimento (DATA)
    public List<ContaDTO> getContaPorVencimento(ContaVencimentoVO vencimentos) {

        String sql = "select c from conta c where c.data between :pDataInicial and :pDataFinal ";
        Query query = manager.createQuery(sql);
        query.setParameter("pDataInicial", vencimentos.getVencimentoInicial());
        query.setParameter("pDataFinal", vencimentos.getVencimentoFinal());
        return query.getResultList();

    }

    //Buscando Contas por periodo de vencimento (ANO / MES)
    public List<ContaDTO> getContaPorAnoMes(ContaVencimentoVO vencimentos) {

        String dataPesquisa = geraDataPesquisa(vencimentos);
        String sql = "select c from conta c where c.data like '%" + dataPesquisa + "%'";
        Query query = manager.createQuery(sql);
        return query.getResultList();

    }

    //Buscando Contas por Tipo de Lançamento
    public List<ContaDTO> getContaPorTipoLancamento(ContaVO c) {

        String sql = "select c from conta c "
                    + "join tipolancamento t"
                    + "where t.id = :pTipoLancamentoID";
        Query query = manager.createQuery(sql);
        query.setParameter("pTipoLancamentoID", c.getTipoLancamento());
        
        return query.getResultList();

    }

    public TipoLancamentoDTO getTipoLancamento(Integer id) {
        return manager.find(TipoLancamentoDTO.class, id);
    }

    public List<TipoLancamentoDTO> getTiposLancamento(){
        String sql = "select t from tipolancamento t";
        Query query = manager.createQuery(sql);
        return query.getResultList();
    }
    
    public void insertTipoLancamento(List<TipoLancamentoDTO> lancamentos){
        manager.persist(lancamentos);
    }
    
    /*
        Refatora as informações referente a data de consulta
        - Se informado apenas o ano retorna apenas o ano
        - Se informado apenas o mes, concatena o 0 (zero) se menor que 10 e acrescenta - (traço)
        para garantir o retorno apenas de contas do mês referente.
     */
    private String geraDataPesquisa(ContaVencimentoVO vencimentos) {

        String dataPesquisa;

        if (vencimentos.getMes() == 0) {

            dataPesquisa = String.valueOf(vencimentos.getAno());

        } else if (vencimentos.getAno() == 0) {

            if (vencimentos.getMes() < 10) {
                dataPesquisa = "-0" + String.valueOf(vencimentos.getMes()) + "-";
            } else {
                dataPesquisa = "-" + String.valueOf(vencimentos.getMes()) + "-";
            }

        } else {

            if (vencimentos.getMes() < 10) {
                dataPesquisa = String.valueOf(vencimentos.getAno()) + "-0" + String.valueOf(vencimentos.getMes());
            } else {
                dataPesquisa = String.valueOf(vencimentos.getAno()) + "-" + String.valueOf(vencimentos.getMes());
            }

        }

        return dataPesquisa;

    }

}
