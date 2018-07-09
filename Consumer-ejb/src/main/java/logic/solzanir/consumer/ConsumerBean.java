package logic.solzanir.consumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 09/03/2018
 */
@Startup
@Singleton
public class ConsumerBean {

    InitialContext context = null;
    ConnectionFactory factory = null;
    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;
    Queue q = null;

    private static final Logger LOG = Logger.getLogger(ConsumerBean.class.getName());

    @Schedule(hour = "*", minute = "*", second = "*/20", persistent = false)
    public void receive() {

        LOG.log(Level.WARNING, "[CONSUMER] : verificando mensagens");

        try {
            context = new InitialContext();

            factory = (ConnectionFactory) context.lookup("java:jboss/DefaultJMSConnectionFactory");
            q = (Queue) context.lookup("java:/jms/queue/ExpiryQueue");

            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(q);

            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String mensagem = textMessage.getText();
                LOG.log(Level.WARNING, "[CONSUMER] Mensagem capturada: {0}", mensagem);
            }

        } catch (NamingException | JMSException ex) {

            LOG.log(Level.SEVERE, "[CONSUMER] Erro ao consumir mensagem : {0}", ex.getMessage());

        } finally {
            try {
                if (consumer != null) {
                    consumer.close();
                }
            } catch (JMSException ex) {
                Logger.getLogger(ConsumerBean.class.getName()).log(Level.SEVERE, "[CONSUMER] Erro ao fechar CONSUMER", ex);
            }
            try {
                if (session != null) {
                    session.close();
                }
            } catch (JMSException ex) {
                Logger.getLogger(ConsumerBean.class.getName()).log(Level.SEVERE, "[CONSUMER] Erro ao fechar SESSION", ex);
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException ex) {
                Logger.getLogger(ConsumerBean.class.getName()).log(Level.SEVERE, "[CONSUMER] Erro ao fechar CONNECTION", ex);
            }
            
        }

    }

}
