package logic.solzanir.message.receiver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 15/02/2018
 */
@Startup
@Singleton
public class ContaReceiver {

    InitialContext initConn = null;
    InitialContext initQueue = null;

    ConnectionFactory factory = null;
    JMSContext context = null;
    Connection connection = null;
    Session session = null;
    Queue q = null;
    
    private static final Logger LOG = Logger.getLogger(ContaReceiver.class.getName());
    
    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void receive() {

        LOG.log(Level.WARNING, "[CONSUMER] : iniciando consumer");
        
        try {

            initConn = new InitialContext();
            initQueue = new InitialContext();

            factory = (ConnectionFactory) initConn.lookup("java:jboss/DefaultJMSConnectionFactory");
            q = (Queue) initQueue.lookup("java:/jms/queue/ExpiryQueue");

            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(q);

            /*
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    //APAGAR
                    LOG.log(Level.WARNING, "[CONSUMER] : consumindo mensagem");

                }
            });
            */

            
        } catch ( JMSException ex) {

            LOG.log(Level.WARNING, "[CONSUMER] : erro dentro do receive( JMSException ) {0}", ex.getMessage());

        } catch (NamingException ex) {
            
            LOG.log(Level.WARNING, "[CONSUMER] : erro dentro do receive( NamingException ) {0}", ex.getMessage());
            
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    LOG.log(Level.WARNING, "[CONSUMER] : erro ao fechar connection");
                }
            }

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    LOG.log(Level.WARNING, "[CONSUMER] : erro ao fechar session");
                }
            }

            LOG.log(Level.WARNING, "[CONSUMER] : Fechando consumer");
            
        }

    }

}
