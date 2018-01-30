package logic.solzanir.conta.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import logic.solzanir.banco.models.BancoVO;
import logic.solzanir.conta.excecoes.ContaException;
import logic.solzanir.conta.modelos.ContaVO;
import logic.solzanir.conta.modelos.ContaVencimentoVO;
import logic.solzanir.conta.util.Constantes;
import logic.solzanir.conta.database.ContaDAO;
import logic.solzanir.conta.modelos.ContaDTO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 25/12/2017
 */
@Stateless
public class ContaBean {

    @Inject
    private ContaDAO dao;

    @Inject
    private Event<BancoVO> event;

    SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_DATA);

    @Transactional
    public ContaVO insereConta(ContaVO conta) throws ContaException {

        ContaDTO dto = convertContaDTO(conta);
        validaConta(dto);
        dto = dao.insertConta(dto);

        if (dto.getTipoLancamento().isBanco()) {
            BancoVO banco = new BancoVO();
            criaBanco(banco, dto);
            event.fire(banco);
        }

        conta = convertContaVO(dto);
        return conta;

    }

    @Transactional
    public ContaVO atualizaConta(ContaVO conta) throws ContaException {

        ContaDTO dto = convertContaDTO(conta);
        dto = dao.updateConta(dto);
        conta = convertContaVO(dto);
        return conta;

    }

    @Transactional
    public void removeConta(ContaVO conta) throws ContaException {

        dao.deleteConta(conta);

    }

    @Transactional
    public ContaVO buscaContaPorID(int id) throws ContaException {

        ContaDTO dto = dao.getConta(id);
        return convertContaVO(dto);

    }

    @Transactional
    public List<ContaVO> buscaTodasContas() throws ContaException {

        List<ContaVO> contas = new ArrayList<>();

        for (ContaDTO dto : dao.getContas()) {
            ContaVO conta = convertContaVO(dto);
            contas.add(conta);
        }

        return contas;

    }

    @Transactional
    public List<ContaVO> buscaContaPorNome(ContaVO conta) throws ContaException {

        List<ContaVO> contas = new ArrayList<>();
        ContaDTO dto = new ContaDTO();
        dto.setNome(conta.getNome());

        for (ContaDTO contaDTO : dao.getContaPorNome(dto)) {
            ContaVO c = convertContaVO(contaDTO);
            contas.add(c);
        }

        return contas;

    }

    @Transactional
    public List<ContaVO> buscaContaPorData(ContaVencimentoVO vencimento) throws ContaException {

        List<ContaVO> contas = new ArrayList<>();

        try {

            /*
            Se o cliente informar um Ano ou Mes a consulta considera estes campos,
            se não considera a data informada, se não informada data final
            repete-se a data inicial
             */
            if (vencimento.getAno() != 0 || vencimento.getMes() != 0) {

                for (ContaDTO dto : dao.getContaPorAnoMes(vencimento)) {
                    ContaVO conta = convertContaVO(dto);
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

                for (ContaDTO dto : dao.getContaPorVencimento(vencimento)) {
                    ContaVO conta = convertContaVO(dto);
                    contas.add(conta);
                }

            }

        } catch (ParseException ex) {
            throw new ContaException(Constantes.DATAINVALIDA);
        }

        return contas;

    }

    @Transactional
    public List<ContaVO> buscaContaPorTipoLancamento(ContaVO conta) throws ContaException {

        List<ContaVO> contas = new ArrayList<>();
        List<ContaDTO> contasDTO = dao.getContaPorTipoLancamento(conta);

        for (ContaDTO dto : contasDTO) {
            conta = convertContaVO(dto);
            contas.add(conta);
        }

        return contas;

    }

    /*
        Valida qual data é maior e reordena as datas, caso necessário, antes
        de passar as mesmas para o DAO
     */
    private void ordenaDatas(ContaVencimentoVO vencimento) throws ContaException {
        try {
            sdf.setLenient(false);
            Date dataInicial = sdf.parse(vencimento.getVencimentoInicial());
            Date dataFinal = sdf.parse(vencimento.getVencimentoFinal());

            if (dataInicial.after(dataFinal)) {
                String aux = vencimento.getVencimentoInicial();
                vencimento.setVencimentoInicial(vencimento.getVencimentoFinal());
                vencimento.setVencimentoFinal(aux);
            }
        } catch (ParseException | NullPointerException ex) {
            System.err.println("Erro conversao data:" + ex.getMessage());
            throw new ContaException(Constantes.DATAINVALIDA);
        }
    }

    private ContaDTO convertContaDTO(ContaVO c) throws ContaException {

        ContaDTO conta = new ContaDTO();
        Calendar calendar = Calendar.getInstance();

        try {

            calendar.setTime(sdf.parse(c.getData()));

            if (c.getId() > 0) {
                conta.setId(c.getId());
            }
            conta.setNome(c.getNome());
            conta.setData(calendar);
            conta.setTipoLancamento(dao.getTipoLancamento(c.getTipoLancamento()));
            conta.setValor(c.getValor());

        } catch (ParseException ex) {
            System.err.println("Erro conversao data:" + ex.getMessage());
            throw new ContaException(Constantes.DATAINVALIDA);
        }

        return conta;

    }

    private ContaVO convertContaVO(ContaDTO dto) {

        ContaVO conta = new ContaVO();

        if (dto != null) {
            Date date = dto.getData().getTime();
            String dataConta = sdf.format(date);

            conta.setId(dto.getId());
            conta.setNome(dto.getNome());
            conta.setData(dataConta);
            conta.setTipoLancamento(dto.getTipoLancamento().getId());
            conta.setValor(Double.valueOf(dto.getValor().toString()));
        }

        return conta;

    }

    private void validaConta(ContaDTO conta) throws ContaException {

        if (conta.getNome().isEmpty()) {
            throw new ContaException(Constantes.NOMEOBRIGATORIO);
        }

        if (conta.getTipoLancamento() == null) {
            throw new ContaException(Constantes.TIPOLANCAMENTOOBRIGATORIO);
        }

        if (conta.getData().getTime() == null) {
            throw new ContaException(Constantes.DATAOBRIGATORIO);
        }

        if (conta.getValor().doubleValue() <= 0) {
            throw new ContaException(Constantes.VALOROBRIGATORIO);
        }

    }

    private void criaBanco(BancoVO banco, ContaDTO dto) {

        Date date = dto.getData().getTime();
        String dataConta = sdf.format(date);

        banco.setNome(dto.getNome());
        banco.setData(dataConta);
        banco.setTipoLancamento(dto.getTipoLancamento().getTipoLancamento());
        banco.setValor(dto.getValor());
        
    }

}
