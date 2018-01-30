/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.solzanir.banco.beans;

import java.math.BigDecimal;
import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Named;
import logic.solzanir.banco.eventos.BancoEvent;

/**
 *
 * @author martin
 */
@Named
public class BancoBean {

    private BigDecimal saldo = BigDecimal.ZERO;
    
    @Asynchronous
    public void gravaMovimentacaoBanco(@Observes(notifyObserver = Reception.ALWAYS) BancoEvent evento){
        saldo = saldo.add(evento.getValor());
        System.out.println("Nova Movimentação no banco: \n" + evento);
        System.out.println("SALDO ATUAL: " + saldo);
    }
    
}
