package logic.solzanir.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.excecoes.ContaException;
import logic.solzanir.modelos.ContaVencimentoVO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Stateless
public class ContaDAO {

    @Resource(lookup = "java:jboss/datasources/HSQLDS")
    private DataSource ds;
    
    //Buscar conta por ID na base de dados
    public ContaVO getConta(int id) throws ContaException, SQLException {

        ContaVO conta = new ContaVO();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            
            String sql = "SELECT * FROM CONTA WHERE ID = ?";
            conn = ds.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                populaConta(conta, rs);
            }

        } catch (SQLException ex) {
            System.err.println("ERRO AO PESQUISAR CONTA POR ID");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA DE CONTA POR ID");
                throw new SQLException(ex);
            }
        }

        return conta;

    }

    //Buscar todas as contas na base de dados
    public List<ContaVO> getContas() throws ContaException, SQLException {

        List<ContaVO> contas = new ArrayList<ContaVO>();
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM CONTA";
            con = ds.getConnection();
            stm = con.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ContaVO conta = new ContaVO();
                populaConta(conta, rs);
                contas.add(conta);
            }

        } catch (SQLException ex) {
            System.err.println("ERRO NA CONSULTA");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                stm.close();
                con.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA DE CONTAS");
                throw new SQLException(ex);
            }
        }

        return contas;

    }

    //Insere uma nova conta na base de dados
    public ContaVO insertConta(ContaVO conta) throws ContaException, SQLException {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            
            String sql = "INSERT INTO CONTA (NOME, DATA, VALOR, TIPOLANCAMENTO) VALUES (?, ?, ?, ?)";
            conn = ds.getConnection();
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, conta.getNome());
            pstm.setString(2, conta.getData());
            pstm.setDouble(3, conta.getValor());
            pstm.setString(4, conta.getTipoLancamento().toString());
            pstm.execute();
            rs = pstm.getGeneratedKeys();

            if (rs.next()) {
                conta.setId(rs.getInt("id"));
            }
            
        } catch (SQLException ex) {
            System.err.println("ERRO NA INSERCAO");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA INSERCAO");
                throw new SQLException(ex);
            }
        }

        return conta;

    }

    //apaga conta da base de dados
    public void deleteConta(ContaVO conta) throws ContaException, SQLException {

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            
            String sql = "DELETE FROM CONTA WHERE ID = ?";
            conn = ds.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, conta.getId());
            pstm.execute();

        } catch (SQLException ex) {
            System.err.println("ERRO NA REMOCAO");
            throw new SQLException(ex);
        } finally {
            try {
                pstm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA REMOCAO");
                throw new SQLException(ex);
            }

        }

    }

    //Atualiza conta na base de dados
    public void updateConta(ContaVO conta) throws ContaException, SQLException {

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            
            String sql = "UPDATE CONTA SET NOME = ?, DATA = ?, VALOR = ?, TIPOLANCAMENTO = ? WHERE ID = ?";
            conn = ds.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, conta.getNome());
            pstm.setString(2, conta.getData());
            pstm.setDouble(3, conta.getValor());
            pstm.setString(4, conta.getTipoLancamento().toString());
            pstm.setInt(5, conta.getId());
            pstm.execute();

        } catch (SQLException ex) {
            System.err.println("ERRO NA ATUALIZACAO");
            throw new SQLException(ex);
        } finally {
            try {
                pstm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA ATUALIZACAO");
                throw new SQLException(ex);
            }

        }

    }

    //busca conta na base de dados pelo nome (ou parte dele)
    //A consulta não é case sensitive, porém deve-se respeitar a acentuacao
    public List<ContaVO> getContaPorNome(ContaVO c) throws ContaException, SQLException {

        List<ContaVO> contas = new ArrayList<>();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            
            String sql = "SELECT * FROM CONTA WHERE UPPER(NOME) LIKE '%" + c.getNome().toUpperCase() + "%'";
            conn = ds.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ContaVO conta = new ContaVO();
                populaConta(conta, rs);
                contas.add(conta);
            }

        } catch (SQLException ex) {
            System.err.println("ERRO NA CONSULTA POR NOME");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA POR NOME");
                throw new SQLException(ex);
            }

        }

        return contas;

    }

//Buscando Contas por periodo de vencimento (DATA)
    public List<ContaVO> getContaPorVencimento(ContaVencimentoVO vencimentos) throws ContaException, SQLException {

        List<ContaVO> contas = new ArrayList<ContaVO>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            
            String sql = "SELECT * FROM CONTA WHERE DATA BETWEEN ? AND ? ";
            conn = ds.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, vencimentos.getVencimentoInicial());
            pstm.setString(2, vencimentos.getVencimentoFinal());
            rs = pstm.executeQuery();

            while (rs.next()) {
                ContaVO conta = new ContaVO();
                populaConta(conta, rs);
                contas.add(conta);
            }
        } catch (SQLException ex) {
            System.err.println("ERRO NA CONSULTA POR VENCIMENTO");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA POR VENCIMENTO");
                throw new SQLException(ex);
            }

        }

        return contas;
    }

    //Buscando Contas por periodo de vencimento (ANO / MES)
    public List<ContaVO> getContaPorAnoMes(ContaVencimentoVO vencimentos) throws ContaException, SQLException {

        String dataPesquisa = gerarDataPesquisa(vencimentos);
        List<ContaVO> contas = new ArrayList<ContaVO>();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM CONTA WHERE DATA LIKE '%" + dataPesquisa + "%'";
            conn = ds.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ContaVO conta = new ContaVO();
                populaConta(conta, rs);
                contas.add(conta);
            }

        } catch (SQLException ex) {
            System.err.println("ERRO NA CONSULTA POR ANO/MES");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA POR ANO/MES");
                throw new SQLException(ex);
            }

        }

        return contas;
    }

    //Buscando Contas por Tipo de Lançamento
    public List<ContaVO> getContaPorTipoLancamento(ContaVO c) throws ContaException, SQLException {

        List<ContaVO> contas = new ArrayList<ContaVO>();
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM CONTA WHERE UPPER(TIPOLANCAMENTO) LIKE '%" + c.getTipoLancamento() + "%'";
            conn = ds.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ContaVO conta = new ContaVO();
                populaConta(conta, rs);
                contas.add(conta);
            }
        } catch (SQLException ex) {
            System.err.println("ERRO NA CONSULTA POR TIPO DE LANCAMENTO");
            throw new SQLException(ex);
        } finally {
            try {
                rs.close();
                stm.close();
                conn.close();
            } catch (SQLException ex) {
                System.err.println("ERRO AO FECHAR NA CONSULTA POR TIPO DE LANCAMENTO");
                throw new SQLException(ex);
            }

        }

        return contas;
    }

    /* 
        Método para popular um objeto Conta com suas respectivas informações,
        compartilhado entre os métodos desta classe 
    */
    private void populaConta(ContaVO conta, ResultSet res) throws SQLException {
        conta.setId(res.getInt("id"));
        conta.setNome(res.getString("nome"));
        conta.setData(res.getString("data"));
        conta.setValor(res.getDouble("valor"));
        conta.setTipoLancamento(res.getString("tipolancamento"));
    }

    /*
        Refatora as informações referente a data de consulta
        - Se informado apenas o ano retorna apenas o ano
        - Se informado apenas o mes, contate o 0 (zero) se menor que 10 e acrescenta - (traço)
        para não ocorrer garantir o retorno de conta do mês referente.
    */
    private String gerarDataPesquisa(ContaVencimentoVO vencimentos) {
        
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
