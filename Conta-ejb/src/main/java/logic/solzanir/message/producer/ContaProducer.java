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
public class ContaProducer {

    InitialContext initConn = null;
    InitialContext initQueue = null;
    
    ConnectionFactory factory = null;
    JMSContext context = null;
    Connection connection = null;
    Session session = null;
    Queue q = null;

    private static final Logger LOG = Logger.getLogger(ContaProducer.class.getName());
    
    public void sendContaCorrente(Conta conta) throws JMSException {

        try {

            initConn = new InitialContext();
            initQueue = new InitialContext();

            factory = (ConnectionFactory) initConn.lookup("java:jboss/DefaultJMSConnectionFactory");
            q = (Queue) initQueue.lookup("java:/jms/queue/ExpiryQueue");
            
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(q);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            TextMessage message = session.createTextMessage("Mensagem de Teste");
            
            producer.send(message);
            
            //APAGAR
            LOG.log(Level.FINE, "[PRODUCER] Mensagem : {0}", message.getText());
            
        } catch (NamingException ex) {

            Logger.getLogger(ContaProducer.class.getName()).log(Level.SEVERE, null, ex);

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
