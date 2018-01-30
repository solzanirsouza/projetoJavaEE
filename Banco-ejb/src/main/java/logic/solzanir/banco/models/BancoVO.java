package logic.solzanir.banco.models;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 29/01/2018
 */
public class BancoVO {

    private Integer id;
    private String nome;
    private String data;
    private String TipoLancamento;
    private BigDecimal valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipoLancamento() {
        return TipoLancamento;
    }

    public void setTipoLancamento(String TipoLancamento) {
        this.TipoLancamento = TipoLancamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
}
