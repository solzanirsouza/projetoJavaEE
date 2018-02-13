package logic.solzanir.conta.database;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import logic.solzanir.conta.models.Conta;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.models.TipoLancamento;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Stateless
public class ContaDAO {

    @PersistenceContext
    EntityManager manager;

    //Insere uma nova conta na base de dados
    public Conta insertConta(Conta conta) {

        manager.persist(conta);
        return conta;

    }

    //Atualiza conta na base de dados
    public Conta updateConta(Conta conta) {

        Conta contadb = manager.find(Conta.class, conta.getId());
        
        if (contadb != null) {
            contadb = conta;
            manager.merge(contadb);
        }
        
        return contadb;

    }

    //apaga conta da base de dados
    public void deleteConta(Conta conta) {

        Conta contadb = manager.find(Conta.class, conta.getId());
        if (contadb != null) {
            manager.remove(contadb);
        }

    }

    //Buscar todas as contas na base de dados
    public List<Conta> getContas() {

        String sql = "select c from conta c";
        Query query = manager.createQuery(sql);
        return query.getResultList();

    }

    //Buscar conta por ID na base de dados
    public Conta getConta(int id) {

        return manager.find(Conta.class, id);

    }

    //busca conta na base de dados pelo nome (ou parte dele)
    //A consulta não é case sensitive, porém deve-se respeitar a acentuacao
    public List<Conta> getContaPorNome(Conta conta) {

        String sql = "select c from conta c where upper(c.nome) like '%" + conta.getNome().toUpperCase() + "%'";
        Query query = manager.createQuery(sql);
        return query.getResultList();

    }

    //Buscando Contas por periodo de vencimento (DATA)
    public List<Conta> getContaPorVencimento(ContaVencimento vencimentos) {
        
        String sql = "select c from conta c where c.dataConta between :pDataInicial and :pDataFinal ";
        Query query = manager.createQuery(sql);
        query.setParameter("pDataInicial", vencimentos.getVencimentoInicial());
        query.setParameter("pDataFinal", vencimentos.getVencimentoFinal());
        return query.getResultList();

    }

    //Buscando Contas por periodo de vencimento (ANO / MES)
    public List<Conta> getContaPorAnoMes(ContaVencimento vencimentos) {

        String dataPesquisa = geraDataPesquisa(vencimentos);
        String sql = "select c from conta c where c.dataConta like '%" + dataPesquisa + "%'";
        Query query = manager.createQuery(sql);
        return query.getResultList();

    }

    //Buscando Contas por Tipo de Lançamento
    public List<Conta> getContaPorTipoLancamento(Conta c) {

        String sql = "select c from conta c where tipolancamento_id = :pTipoLancamentoID";
        Query query = manager.createQuery(sql);
        query.setParameter("pTipoLancamentoID", c.getTipoLancamento());
        return query.getResultList();

    }

    public TipoLancamento getTipoLancamento(Integer id) {
        return manager.find(TipoLancamento.class, id);
    }

    public List<TipoLancamento> getTiposLancamento() {
        String sql = "select t from tipolancamento t";
        Query query = manager.createQuery(sql);
        return query.getResultList();
    }

    public void insertTipoLancamento(List<TipoLancamento> lancamentos) {
        manager.persist(lancamentos);
    }

    /*
        Refatora as informações referente a data de consulta
        - Se informado apenas o ano retorna apenas o ano
        - Se informado apenas o mes, concatena o 0 (zero) se menor que 10 e acrescenta - (traço)
        para garantir o retorno apenas de contas do mês referente.
     */
    private String geraDataPesquisa(ContaVencimento vencimentos) {

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
