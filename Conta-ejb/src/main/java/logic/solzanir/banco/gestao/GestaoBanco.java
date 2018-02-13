package logic.solzanir.banco.gestao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;

import logic.solzanir.conta.models.Conta;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   30/01/2018
 */
@Singleton
public class GestaoBanco {

    List<Conta> contasBanco = new ArrayList<>();
    
    public void addConta(Conta conta){
        contasBanco.add(conta);
    }
    
    public List<Conta> getBanco(){
        return contasBanco;
    }
    
    public void clearBanco(){
        contasBanco.clear();
    }
    
}
