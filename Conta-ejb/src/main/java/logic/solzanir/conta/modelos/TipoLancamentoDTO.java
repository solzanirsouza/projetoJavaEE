package logic.solzanir.conta.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   26/01/2018
 */
@Entity(name = "tipolancamento")
public class TipoLancamentoDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String tipoLancamento;

    private boolean banco;

    public boolean isBanco() {
        return banco;
    }

    public void setBanco(boolean banco) {
        this.banco = banco;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }
    
}
