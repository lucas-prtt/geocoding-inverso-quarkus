package controller;

import config.ControllerConfig;
import decoder.IdentificadorDeUbicacion;
import decoder.Provincia;
import dto.DecodeDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/decode")
public class DecodeController {
    @Inject
    ControllerConfig controllerConfig;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response decode(@QueryParam("lat") Float lat, @QueryParam("lon") Float lon) {
        if(lat == null || lon == null){
            throw new IllegalArgumentException("Debe incluir los queryParam <lat> y <lon>");
        }
        Provincia prov = IdentificadorDeUbicacion.getInstance().identificar(lat, lon);
        DecodeDTO resp = new DecodeDTO(prov, controllerConfig);
        return Response.ok(resp).build();
    }
}
