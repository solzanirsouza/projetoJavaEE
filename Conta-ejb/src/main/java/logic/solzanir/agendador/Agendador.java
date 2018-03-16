package logic.solzanir.agendador;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.inject.Inject;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.conta.models.Conta;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 13/02/2018
 */
@Startup
@Singleton
public class Agendador {

    @Inject
    private GestaoBanco gestao;

    private static final Logger LOG = Logger.getLogger(Agendador.class.getName());

    @Schedule(second = "*/20", minute = "*", hour = "*", persistent = false)
    public void agendador() {

        LOG.log(Level.WARNING, "[AGENDADOR] : executando tarefa agendada");

        for (Conta conta : gestao.getBanco()) {
            System.out.println("---------------------------------------------------");
            System.out.println("\nConta: " + conta.getNome());
            System.out.println("\nTipo Lancamento: " + conta.getTipoLancamento().getTipoLancamento());
            System.out.println("\nData: " + conta.getData().toString());
            System.out.println("\nValor: " + conta.getValor());
            System.out.println("---------------------------------------------------");
        }
    }

}
