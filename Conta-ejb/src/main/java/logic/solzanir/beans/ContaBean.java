package logic.solzanir.beans;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import logic.solzanir.excecoes.ContaException;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.modelos.ContaVencimentoVO;
import logic.solzanir.util.Constantes;
import logic.solzanir.util.ValidadorData;
import logic.solzanir.database.ContaDAO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 25/12/2017
 */
@Stateless
public class ContaBean {

    @Inject
    private ContaDAO dao;

    @Inject
    private ValidadorData validaData;

    public ContaVO buscaContaPorID(int id) throws SQLException, ContaException {
        return dao.getConta(id);
    }

    public List<ContaVO> buscaTodasContas() throws SQLException, ContaException {
        return dao.getContas();
    }

    public List<ContaVO> buscaContaPorNome(ContaVO conta) throws SQLException, ContaException {
        return dao.getContaPorNome(conta);
    }

    public List<ContaVO> buscaContaPorData(ContaVencimentoVO vencimento) throws SQLException, ContaException {

        if (vencimento.getAno() != 0 || vencimento.getMes() != 0) {
            return dao.getContaPorAnoMes(vencimento);
        } else if (vencimento.getVencimentoFinal() == null
                || vencimento.getVencimentoFinal().equals("")) {
            vencimento.setVencimentoFinal(vencimento.getVencimentoInicial());
        }
        validaDatas(vencimento);
        return dao.getContaPorVencimento(vencimento);

    }

    public List<ContaVO> buscaContaPorTipoLancamento(ContaVO conta) throws SQLException, ContaException {
        return dao.getContaPorTipoLancamento(conta);
    }

    public ContaVO insereConta(ContaVO conta) throws ContaException, SQLException {
        validaConta(conta);
        return dao.insertConta(conta);
    }

    public void removeConta(ContaVO conta) throws ContaException, SQLException {
        dao.deleteConta(conta);
    }

    public void atualizaConta(ContaVO conta) throws ContaException, SQLException {
        validaConta(conta);
        dao.updateConta(conta);
    }

    private void validaConta(ContaVO conta) throws ContaException {

        if (conta.getNome() == null || conta.getNome().equals("")) {
            throw new ContaException(Constantes.NOMEOBRIGATORIO);
        }
        
        if (conta.getValor() < 0) {
            throw new ContaException(Constantes.VALOROBRIGATORIO);
        }
        
        if (conta.getTipoLancamento() == null || conta.getTipoLancamento().equals("")) {
            throw new ContaException(Constantes.TIPOLANCAMENTOOBRIGATORIO);
        }
        
        if (conta.getData() == null || conta.getData().equals("")) {
            throw new ContaException(Constantes.DATAOBRIGATORIO);
        }
        
        validaData.validaData(conta.getData());

    }

    private void validaDatas(ContaVencimentoVO vencimento) throws ContaException {
        try {
            DateFormat df = new SimpleDateFormat(Constantes.FORMATO_DATA);
            df.setLenient(false);
            Date dataInicial = df.parse(vencimento.getVencimentoInicial());
            Date dataFinal = df.parse(vencimento.getVencimentoFinal());

            if (dataInicial.after(dataFinal)) {
                String aux = vencimento.getVencimentoInicial();
                vencimento.setVencimentoInicial(vencimento.getVencimentoFinal());
                vencimento.setVencimentoFinal(aux);
            }
        } catch (ParseException ex) {
            throw new ContaException(Constantes.DATAINVALIDA);
        }
    }

}
