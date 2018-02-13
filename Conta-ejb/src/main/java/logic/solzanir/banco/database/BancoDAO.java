package logic.solzanir.banco.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import logic.solzanir.banco.models.Banco;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 30/01/2018
 */
@Stateless
public class BancoDAO {

    @PersistenceContext
    private EntityManager manager;

    private Double saldo = 0.00;

    @Transactional
    public void incrementaSaldo(Double valor) {

        try {

            Banco banco = manager.find(Banco.class, 1);

            if (banco == null) {
                banco = new Banco();
                banco.setId(1);
                banco.setSaldo(valor);
                manager.persist(banco);
            } else {
                saldo = banco.getSaldo();
                saldo += valor;
                banco.setSaldo(saldo);
                manager.merge(banco);
            }

        } catch (PersistenceException e) {

            //LOG
            throw e;

        }

    }

    @Transactional
    public Double getSaldo() {

        try {

            Banco banco = manager.find(Banco.class, 1);
            return banco.getSaldo();

        } catch (PersistenceException e) {

            //LOG
            throw e;
        }

    }

}
