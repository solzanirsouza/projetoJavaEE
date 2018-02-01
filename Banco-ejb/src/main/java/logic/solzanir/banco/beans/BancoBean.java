package logic.solzanir.banco.beans;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import logic.solzanir.banco.database.BancoDAO;
import logic.solzanir.banco.gestao.GestaoBanco;
import logic.solzanir.banco.models.BancoDTO;
import logic.solzanir.banco.models.BancoVO;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   30/01/2018
 */
public class BancoBean {

    @Inject
    private BancoDAO dao;

    @Inject
    private GestaoBanco gestao;
    
    @Asynchronous
    public void gravaMovimentacaoBanco(@Observes BancoVO banco) {
        
        BancoDTO dto = new BancoDTO();
        dto.setId(1);
        dto.setSaldo(banco.getValor());
        dao.incrementaSaldo(dto);
        gestao.addConta(banco);
        
    }
    
}
