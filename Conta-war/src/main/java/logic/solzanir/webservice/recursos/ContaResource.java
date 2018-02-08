package logic.solzanir.webservice.recursos;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import logic.solzanir.conta.exception.ContaException;
import logic.solzanir.conta.models.ContaVencimento;
import logic.solzanir.conta.models.MensagemResponse;
import logic.solzanir.conta.beans.ContaBean;
import logic.solzanir.conta.models.Conta;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Path("conta")
public class ContaResource {

    @Inject
    private ContaBean bean;

    @Inject
    private MensagemResponse response;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContas() {

        List<Conta> retorno = new ArrayList<>();
        retorno = bean.findAllConta();
        return Response.ok().entity(retorno).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConta(@PathParam("id") Integer id) {

        Conta retorno = bean.findContaById(id);
        return Response.ok().entity(retorno).build();

    }

    @POST
    @Path("/pornome")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorNome(Conta conta) throws ContaException {

        List<Conta> resposta = new ArrayList<>();
        resposta = bean.findContaByName(conta);
        return Response.ok().entity(resposta).build();

    }

    @POST
    @Path("/porvencimento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorVencimento(ContaVencimento vencimentos) {

        List<Conta> retorno = new ArrayList<>();
        try {
            retorno = bean.findContaByData(vencimentos);
        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        return Response.ok().entity(retorno).build();

    }

    @POST
    @Path("/porlancamento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorTipoLacamento(Conta conta) throws ContaException {

        List<Conta> retorno = new ArrayList<>();
        retorno = bean.findContaByTipoLancamento(conta);
        return Response.ok().entity(retorno).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insereConta(Conta conta) {
        
        Conta retorno = new Conta();
        try {
            retorno = bean.insertConta(conta);
        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        return Response.status(Response.Status.CREATED).entity(retorno).build();
        
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizaConta(Conta conta) {
        
        try {
            conta = bean.updateConta(conta);
        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        return Response.ok("Conta atualizada com sucesso").entity(conta).build();
        
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeConta(Conta conta) {
        
        bean.removeConta(conta);
        return Response.ok("Conta removida com sucesso").build();
        
    }

}
