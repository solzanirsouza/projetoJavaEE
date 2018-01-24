package logic.solzanir.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ejb.Stateless;
import logic.solzanir.excecoes.ContaException;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 27/12/2017
 */
@Stateless
public class ValidadorData {

    public boolean validaData(String data) throws ContaException {

        DateFormat df = new SimpleDateFormat(Constantes.FORMATO_DATA);
        df.setLenient(false);
        try {
            df.parse(data);
            return true;
        } catch (ParseException ex) {
            throw new ContaException(Constantes.DATAINVALIDA);            
        }
        
    }
}
