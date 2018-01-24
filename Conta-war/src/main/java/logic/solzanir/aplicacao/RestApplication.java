package logic.solzanir.aplicacao;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import logic.solzanir.recursos.ContaResource;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date   14/12/2017
 */

@ApplicationPath("services")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> recursos = new HashSet<Class<?>>();
        addRestResourcesClasses(recursos);
        return recursos;
        
    }
    
    private void addRestResourcesClasses(Set<Class<?>> recursos){
        recursos.add(ContaResource.class);
    }
    
}
