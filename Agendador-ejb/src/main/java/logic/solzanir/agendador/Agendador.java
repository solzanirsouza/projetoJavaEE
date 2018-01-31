package logic.solzanir.agendador;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.banco.models.BancoVO;


/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   30/01/2018
 */
@Singleton
@Startup
public class Agendador {

    @Inject
    private GestaoBanco gestao;
    
    @Schedule(second = "*", minute = "*/10", hour = "*", persistent = false)
    public void agendador(){
        for (BancoVO banco : gestao.getBanco()){
            System.out.println("---------------------------------------------------");
            System.out.println("\nConta: " + banco.getNome());
            System.out.println("\nTipo Lancamento: " + banco.getTipoLancamento());
            System.out.println("\nData: " + banco.getData());
            System.out.println("\nValor: " + banco.getValor());
            
        }
    }
    
}
