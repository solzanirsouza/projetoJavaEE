package logic.solzanir.webservice.recursos;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
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
import logic.solzanir.conta.util.Constantes;

/**
 * @author Solzanir Souza <souzanirs@gmail.com>
 * @date 14/12/2017
 */
@Path("conta")
public class ContaResource {

    @Inject
    private ContaBean bean;

    private MensagemResponse response = new MensagemResponse();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContas() {

        try {

            List<Conta> retorno = new ArrayList<>();
            retorno = bean.findAllConta();
            return Response.ok().entity(retorno).build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConta(@PathParam("id") Integer id) {

        try {

            Conta retorno = bean.findContaById(id);
            return Response.ok().entity(retorno).build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @POST
    @Path("/pornome")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorNome(Conta conta) throws ContaException {

        try {

            List<Conta> resposta = new ArrayList<>();
            resposta = bean.findContaByName(conta);
            return Response.ok().entity(resposta).build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @POST
    @Path("/porvencimento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorVencimento(ContaVencimento vencimentos) {

        List<Conta> retorno = new ArrayList<>();

        try {

            retorno = bean.findContaByData(vencimentos);
            return Response.ok().entity(retorno).build();

        } catch (ContaException ex) {

            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();

        } catch (PersistenceException ex) {

            response.setMensagem(Constantes.ERRO_INTERNO + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @POST
    @Path("/porlancamento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContaPorTipoLacamento(Conta conta) throws ContaException {

        try {

            List<Conta> retorno = new ArrayList<>();
            retorno = bean.findContaByTipoLancamento(conta);
            return Response.ok().entity(retorno).build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insereConta(Conta conta) {

        try {

            Conta retorno = new Conta();
            retorno = bean.insertConta(conta);
            return Response.status(Response.Status.CREATED).entity(retorno).build();

        } catch (ContaException ex) {

            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();

        } catch (PersistenceException ex) {

            response.setMensagem(Constantes.ERRO_INTERNO + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizaConta(Conta conta) {

        try {

            conta = bean.updateConta(conta);
            return Response.ok("Conta atualizada com sucesso").entity(conta).build();

        } catch (ContaException ex) {

            response.setMensagem(ex.getMensagem());
            return Response.status(Response.Status.CONFLICT).entity(response).build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeConta(Conta conta) {

        try {

            bean.removeConta(conta);
            return Response.ok("Conta removida com sucesso").build();

        } catch (PersistenceException e) {

            response.setMensagem(Constantes.ERRO_INTERNO + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        }

    }

}
