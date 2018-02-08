package logic.solzanir.conta.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import logic.solzanir.conta.exception.ContaException;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.util.Constantes;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.models.Conta;

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

    SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_DATA);

    @Transactional
    public Conta insertConta(Conta conta) throws ContaException {

        validaConta(conta);
        conta = dao.insertConta(conta);

        if (conta.getTipoLancamento().isBanco()) {
            event.fire(conta);
        }

        return conta;

    }

    @Transactional
    public Conta updateConta(Conta conta) throws ContaException {

        validaConta(conta);
        conta = dao.updateConta(conta);
        return conta;

    }

    @Transactional
    public void removeConta(Conta conta) {

        dao.deleteConta(conta);

    }

    @Transactional
    public Conta findContaById(int id) {

        return dao.getConta(id);

    }

    @Transactional
    public List<Conta> findAllConta() {

        List<Conta> contas = new ArrayList<>();

        for (Conta conta : dao.getContas()) {
            contas.add(conta);
        }

        return contas;

    }

    @Transactional
    public List<Conta> findContaByName(Conta conta) {

        List<Conta> contas = new ArrayList<>();

        for (Conta contadb : dao.getContaPorNome(conta)) {
            contas.add(contadb);
        }

        return contas;

    }

    @Transactional
    public List<Conta> findContaByData(ContaVencimento vencimento) throws ContaException {

        List<Conta> contas = new ArrayList<>();

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

        return contas;

    }

    @Transactional
    public List<Conta> findContaByTipoLancamento(Conta conta) {

        return dao.getContaPorTipoLancamento(conta);

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

    private void validaConta(Conta conta) throws ContaException {

        if (conta.getNome().isEmpty()) {
            throw new ContaException(Constantes.NOMEOBRIGATORIO);
        }

        if (conta.getTipoLancamento() == null) {
            throw new ContaException(Constantes.TIPOLANCAMENTOOBRIGATORIO);
        }

        if (conta.getData() == null) {
            throw new ContaException(Constantes.DATAOBRIGATORIO);
        }

        if (conta.getValor() <= 0) {
            throw new ContaException(Constantes.VALOROBRIGATORIO);
        }

    }

}
