package br.com.fiap.loja.resource;

import br.com.fiap.loja.dao.AvaliacaoDao;
import br.com.fiap.loja.dto.avaliacao.DetalhesAvaliacaoDto;
import br.com.fiap.loja.dto.doce.CadastroDoceDto;
import br.com.fiap.loja.model.Avaliacao;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

@Path("/doces/{codigoDoce}/avaliacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    private AvaliacaoDao avaliacaoDao;

    @Inject
    private ModelMapper mapper;

    @POST
    public Response create(@PathParam("codigoDoce") int codigoDoce,
                           @Valid CadastroDoceDto dto, @Context UriInfo uriInfo) throws SQLException {

        Avaliacao avaliacao = mapper.map(dto, Avaliacao.class);
        avaliacao.setCodigoDoce(codigoDoce);

        avaliacaoDao.cadastrar(avaliacao);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(avaliacao.getCodigo()));

        return Response.created(uriBuilder.build()).entity(mapper.map(avaliacao, DetalhesAvaliacaoDto.class)).build();
    }

}
