package logic.solzanir.conta.modelos;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
public class ContaVO {

    private Integer id = 0;
    private String data;
    private Integer tipoLancamento;        
    private String nome;
    private double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(Integer tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
