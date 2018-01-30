package logic.solzanir.banco.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 29/01/2018
 */
@Entity(name = "contacorrente")
public class BancoDTO implements Serializable {

    @Id
    private Integer id;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal saldo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
}
