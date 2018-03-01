package logic.solzanir.agendador;

import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void agendador() {
        
        for (Conta conta : gestao.getBanco()) {
            System.out.println("---------------------------------------------------");
            System.out.println("\nConta: " + conta.getNome());
            System.out.println("\nTipo Lancamento: " + conta.getTipoLancamento());
            System.out.println("\nData: " + conta.getData());
            System.out.println("\nValor: " + conta.getValor());

        }
    }

}
