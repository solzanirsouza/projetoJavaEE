package logic.solzanir.conta.exception;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   26/12/2017
 */
public class ContaException extends Exception {
    
    private String mensagem = "ESTAMOS COM ERRO";
    
    public ContaException(Exception ex) {
        this.mensagem = ex.getMessage();
    }
    
    public ContaException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
    
}
