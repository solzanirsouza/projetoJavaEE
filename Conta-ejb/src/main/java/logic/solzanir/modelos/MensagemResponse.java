package logic.solzanir.modelos;

import javax.ejb.Stateless;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 31/12/2017
 */
@Stateless
public class MensagemResponse {

    private String mensagem = "";
    
    //Necessário para converter para JSON
    public MensagemResponse(){
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}
