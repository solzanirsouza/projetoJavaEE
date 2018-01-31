package logic.solzanir.banco.gestao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import logic.solzanir.banco.models.BancoVO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   30/01/2018
 */
@Singleton
public class GestaoBanco {

    List<BancoVO> contasBanco = new ArrayList<>();
    
    public void addConta(BancoVO conta){
        contasBanco.add(conta);
    }
    
    public List<BancoVO> getBanco(){
        return contasBanco;
    }
    
}
