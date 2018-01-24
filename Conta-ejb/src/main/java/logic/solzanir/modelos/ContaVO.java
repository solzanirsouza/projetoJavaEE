package logic.solzanir.modelos;

import logic.solzanir.util.Constantes;
import logic.solzanir.util.TipoLancamentoEnum;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
public class ContaVO {

    private int id = Constantes.ValorInteiroInicial;
    private String nome = "";
    private String data = "";
    private double valor = 0.00;
    private TipoLancamentoEnum tipoLancamento = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoLancamentoEnum getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipo) {
        this.tipoLancamento = TipoLancamentoEnum.valueOf(tipo.toUpperCase());
    }

    @Override
    public String toString() {
        return "[Conta: " + nome
                + ", Data: " + data
                + ", Valor: " + valor
                + ", Tipo Lançamento: " + tipoLancamento
                + "]";
    }

}
