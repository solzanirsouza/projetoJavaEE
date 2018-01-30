package logic.solzanir.banco.database;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import logic.solzanir.banco.models.BancoDTO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 30/01/2018
 */
@Stateless
public class BancoDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void incrementaSaldo(BancoDTO evento) {

        BigDecimal saldo = BigDecimal.ZERO;
        BancoDTO banco = manager.find(BancoDTO.class, evento.getId());

        if (banco == null) {
            manager.persist(evento);
        } else {
            saldo = banco.getSaldo();
            saldo = saldo.add(evento.getSaldo());
            banco.setSaldo(saldo);
        }

    }

    @Transactional
    public BigDecimal getSaldo() {

        BancoDTO banco = manager.find(BancoDTO.class, 1);
        return banco.getSaldo();

    }

}
