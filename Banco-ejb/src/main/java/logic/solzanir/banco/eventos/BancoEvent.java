package logic.solzanir.banco.eventos;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   29/01/2018
 */

public class BancoEvent {

    private String conta;
    private String tipoLancamento;
    private BigDecimal valor;
    private Calendar data;

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    @Override
    public String toString() {
        
        String conta = "Conta: " + getConta()
                        + "\n Tipo Lancamento: " + getTipoLancamento()
                        + "\n Valor: " + getValor();
        
        return conta;
    }
    
}
