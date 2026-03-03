package lprtt;

import decoder.IdentificadorDeUbicacion;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/decode")
public class Endpoint {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response decode(@QueryParam("lat") Float lat, @QueryParam("lon") Float lon) {
        if(lat == null || lon == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Debe incluir los queryParam \"lat\" y \"lon\"").build();
        }
        return Response.ok(IdentificadorDeUbicacion.getInstance().identificar(lat, lon).toString()).build();
    }
}
