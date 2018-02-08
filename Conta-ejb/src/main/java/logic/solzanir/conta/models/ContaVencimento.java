package logic.solzanir.conta.models;

import java.util.Date;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 27/12/2017
 */
public class ContaVencimento {

    private Date vencimentoInicial;
    private Date vencimentoFinal;
    private int mes = 0;
    private int ano = 0;

    public Date getVencimentoInicial() {
        return vencimentoInicial;
    }

    public void setVencimentoInicial(Date vencimentoInicial) {
        this.vencimentoInicial = vencimentoInicial;
    }

    public Date getVencimentoFinal() {
        return vencimentoFinal;
    }

    public void setVencimentoFinal(Date vencimentoFinal) {
        this.vencimentoFinal = vencimentoFinal;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
}
