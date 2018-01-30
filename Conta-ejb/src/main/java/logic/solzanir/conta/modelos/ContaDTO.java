package logic.solzanir.conta.modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Entity(name = "conta")
public class ContaDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar data;

    @OneToOne
    private TipoLancamentoDTO tipoLancamento;

    private String nome;
    
   @Column(columnDefinition="Decimal(10,2) default '0.00'")
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

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = BigDecimal.valueOf(valor);
    }

    public TipoLancamentoDTO getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamentoDTO tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    @Override
    public String toString() {
        return "[Conta: " + nome
                + ", Data: " + data
                + ", Valor: " + valor
                + ", Tipo Lançamento: " + tipoLancamento.getTipoLancamento()
                + "]";
    }

}
