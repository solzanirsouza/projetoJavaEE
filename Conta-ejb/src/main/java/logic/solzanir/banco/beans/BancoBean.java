package logic.solzanir.banco.beans;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSException;
import logic.solzanir.banco.database.BancoDAO;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.conta.models.Conta;
import logic.solzanir.message.producer.ContaProducer;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 30/01/2018
 */
@Stateless
public class BancoBean {

    @Inject
    private BancoDAO dao;

    @Inject
    private GestaoBanco gestao;

    @Asynchronous
    public void gravaMovimentacaoBanco(@Observes Conta conta) throws Exception {

        try {

            ContaProducer producer = new ContaProducer();
            producer.sendContaCorrente(conta);
            gestao.addConta(conta);

        } catch (JMSException e) {

            //LOG
            throw e;
        }

    }

}
