package logic.solzanir.recursos;

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
import logic.solzanir.excecoes.ContaException;
import logic.solzanir.modelos.ContaVO;
import logic.solzanir.modelos.ContaVencimentoVO;
import logic.solzanir.modelos.MensagemResponse;
import logic.solzanir.beans.ContaBean;

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

        List<ContaVO> retorno = new ArrayList<>();

        try {

            retorno = bean.buscaTodasContas();
            
        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }

        return Response.ok().entity(retorno).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConta(@PathParam("id") Integer id) {

        ContaVO retorno = new ContaVO();

        try {

            retorno = bean.buscaContaPorID(id);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }

        return Response.ok().entity(retorno).build();

    }

    @POST
    @Path("/pornome")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorNome(ContaVO conta) throws ContaException {

        List<ContaVO> resposta = new ArrayList<>();

        try {

            resposta = bean.buscaContaPorNome(conta);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }

        return Response.ok().entity(resposta).build();

    }

    @POST
    @Path("/porvencimento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorVencimento(ContaVencimentoVO vencimentos) {

        List<ContaVO> retorno = new ArrayList<>();

        try {

            retorno = bean.buscaContaPorData(vencimentos);

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
    public Response getContaPorTipoLacamento(ContaVO conta) throws ContaException {

        List<ContaVO> retorno = new ArrayList<>();

        try {
            
            retorno = bean.buscaContaPorTipoLancamento(conta);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }

        return Response.ok().entity(retorno).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insereConta(ContaVO conta) {
        
        ContaVO retorno = new ContaVO();
        
        try {
        
            retorno = bean.insereConta(conta);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        
        return Response.status(Response.Status.CREATED).entity(retorno).build();
        
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizaConta(ContaVO conta) {
        
        try {
            
            conta = bean.atualizaConta(conta);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        
        return Response.ok("Conta atualizada com sucesso").entity(conta).build();
        
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeConta(ContaVO conta) {
        
        try {

            bean.removeConta(conta);

        } catch (ContaException ex) {
            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }

        return Response.ok("Conta removida com sucesso").build();
        
    }

}
