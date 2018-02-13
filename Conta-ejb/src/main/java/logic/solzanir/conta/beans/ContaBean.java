package logic.solzanir.conta.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import logic.solzanir.conta.exception.ContaException;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.util.Constantes;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.models.Conta;
import logic.solzanir.conta.models.TipoLancamento;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 25/12/2017
 */
@Stateless
public class ContaBean {

    @Inject
    private ContaDAO dao;

    @Inject
    private Event<Conta> event;

    private static final Logger LOG = Logger.getLogger(ContaBean.class.getName());

    private TipoLancamento tipoLancamento;

    @Transactional
    public Conta insertConta(Conta conta) throws ContaException {

        try {

            validaConta(conta);
            conta.setTipoLancamento(tipoLancamento);
            conta = dao.insertConta(conta);

            if (conta.getTipoLancamento().isBanco()) {
                event.fire(conta);
            }

        } catch (ContaException e) {

            LOG.log(Level.INFO, e.getMessage(), e);
            throw e;

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

        return conta;

    }

    @Transactional
    public Conta updateConta(Conta conta) throws ContaException {

        try {

            validaConta(conta);
            conta.setTipoLancamento(tipoLancamento);
            conta = dao.updateConta(conta);

        } catch (ContaException e) {

            LOG.log(Level.INFO, e.getMessage(), e);
            throw e;

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

        return conta;

    }

    @Transactional
    public void removeConta(Conta conta) {

        try {

            dao.deleteConta(conta);

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

    }

    @Transactional
    public Conta findContaById(int id) {

        try {

            return dao.getConta(id);

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

    }

    @Transactional
    public List<Conta> findAllConta() {

        List<Conta> contas = new ArrayList<>();

        try {

            for (Conta conta : dao.getContas()) {
                contas.add(conta);
            }

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

        return contas;

    }

    @Transactional
    public List<Conta> findContaByName(Conta conta) {

        List<Conta> contas = new ArrayList<>();

        try {

            for (Conta contadb : dao.getContaPorNome(conta)) {
                contas.add(contadb);
            }

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

        return contas;

    }

    @Transactional
    public List<Conta> findContaByData(ContaVencimento vencimento) throws ContaException {

        List<Conta> contas = new ArrayList<>();

        try {

            /*
            Se o cliente informar um Ano ou Mes a consulta considera estes campos,
            se não considera a data informada, se não informada data final
            repete-se a data inicial
             */
            if (vencimento.getAno() != 0 || vencimento.getMes() != 0) {

                for (Conta conta : dao.getContaPorAnoMes(vencimento)) {
                    contas.add(conta);
                }

            } else {

                if (vencimento.getVencimentoFinal() == null) {

                    if (vencimento.getVencimentoInicial() == null) {
                        throw new ContaException(Constantes.DATAINVALIDA);
                    } else {
                        vencimento.setVencimentoFinal(vencimento.getVencimentoInicial());
                    }

                } else {

                    if (vencimento.getVencimentoInicial() == null) {
                        vencimento.setVencimentoInicial(vencimento.getVencimentoFinal());
                    } else {
                        ordenaDatas(vencimento);
                    }

                }

                for (Conta conta : dao.getContaPorVencimento(vencimento)) {
                    contas.add(conta);
                }

            }

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

        return contas;

    }

    @Transactional
    public List<Conta> findContaByTipoLancamento(Conta conta) {

        try {

            return dao.getContaPorTipoLancamento(conta);

        } catch (PersistenceException e) {

            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;

        }

    }

    /*
        Valida qual data é maior e reordena as datas, caso necessário, antes
        de passar as mesmas para o DAO
     */
    private void ordenaDatas(ContaVencimento vencimento) throws ContaException {
        try {
            if (vencimento.getVencimentoInicial().after(vencimento.getVencimentoFinal())) {
                Date dataAux = vencimento.getVencimentoInicial();
                vencimento.setVencimentoInicial(vencimento.getVencimentoFinal());
                vencimento.setVencimentoFinal(dataAux);
            }
        } catch (NullPointerException ex) {
            throw new ContaException(Constantes.DATAINVALIDA);
        }
    }

    /*
        Valida os campos informados pelo cliente para cadastro de conta
        verificando se os campos são nulos ou possuem um informação inválida
     */
    private void validaConta(Conta conta) throws ContaException {

        if (conta.getNome() == null || (conta.getNome().length() < 5)) {
            throw new ContaException(Constantes.NOMEOBRIGATORIO);
        }

        if (conta.getTipoLancamento() == null) {
            throw new ContaException(Constantes.TIPOLANCAMENTOOBRIGATORIO);
        } else {
            tipoLancamento = dao.getTipoLancamento(conta.getTipoLancamento().getId());
            if (tipoLancamento == null) {
                throw new ContaException(Constantes.TIPOLANCAMENTOINVALIDO);
            }
        }

        if (conta.getData() == null) {
            throw new ContaException(Constantes.DATAOBRIGATORIO);
        }

        if (conta.getValor() == null || conta.getValor() <= 0) {
            throw new ContaException(Constantes.VALOROBRIGATORIO);
        }

    }

}
