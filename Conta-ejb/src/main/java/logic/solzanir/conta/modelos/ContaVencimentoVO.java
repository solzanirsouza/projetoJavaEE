package logic.solzanir.conta.modelos;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 27/12/2017
 */
public class ContaVencimentoVO {

    private String vencimentoInicial;
    private String vencimentoFinal;
    private int mes = 0;
    private int ano = 0;

    public String getVencimentoInicial() {
        return vencimentoInicial;
    }

    public void setVencimentoInicial(String vencimentoInicial) {
        this.vencimentoInicial = vencimentoInicial;
    }

    public String getVencimentoFinal() {
        return vencimentoFinal;
    }

    public void setVencimentoFinal(String vencimentoFinal) {
        this.vencimentoFinal = vencimentoFinal;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getAno() {
        return this.ano;
    }

    public int getMes() {
        return this.mes;
    }
    
}
