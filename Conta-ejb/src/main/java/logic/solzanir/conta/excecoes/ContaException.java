package logic.solzanir.conta.excecoes;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   26/12/2017
 */
public class ContaException extends Exception {
    
    private String mensagem = "ESTAMOS COM ERRO";
    
    public ContaException(Exception ex) {
        System.err.println("[ERRO] " + ex.getMessage());
        this.mensagem = ex.getMessage();
    }
    
    public ContaException(String mensagem){
        System.err.println("[ERRO] " + mensagem);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
    
}
