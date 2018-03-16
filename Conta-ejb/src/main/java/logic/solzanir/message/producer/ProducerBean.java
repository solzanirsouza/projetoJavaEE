package logic.solzanir.message.producer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.solzanir.conta.models.Conta;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 15/02/2018
 */
public class ProducerBean {

    InitialContext initConn = null;
    InitialContext initQueue = null;
    
    ConnectionFactory factory = null;
    JMSContext context = null;
    Connection connection = null;
    Session session = null;
    MessageProducer producer = null;
    Queue q = null;

    private static final Logger LOG = Logger.getLogger(ProducerBean.class.getName());
    
    public void sendContaCorrente(Conta conta) throws JMSException {
        
        try {

            initConn = new InitialContext();
            initQueue = new InitialContext();

            factory = (ConnectionFactory) initConn.lookup("java:jboss/DefaultJMSConnectionFactory");
            q = (Queue) initQueue.lookup("java:/jms/queue/ExpiryQueue");
            
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(q);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            TextMessage message = session.createTextMessage(conta.getNome() + ": " + conta.getValor().toString());
            
            producer.send(message);
            
            //APAGAR
            LOG.log(Level.WARNING, "[PRODUCER] Enviada mensagem ---> {0}", message.getText());
            
            producer.close();
            initQueue.close();
            initConn.close();
            
        } catch (NamingException ex) {

            LOG.log(Level.SEVERE, "[PRODUCER] Erro {0}", ex.getMessage());

        } finally {
            
            if(connection != null) {
                connection.close();
            }
            
            if(session != null){
                session.close();
            }
            
        }
        
    }

}
