package lprtt;

import decoder.IdentificadorDeUbicacion;
import io.netty.handler.logging.LogLevel;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Startup
public class RutinaInicial {
    private static final Logger LOGGER = Logger.getLogger("RutinaInicial");
    @Inject
    IdentificadorDeUbicacion identificadorDeUbicacion;
    @PostConstruct
    void init() {
        identificadorDeUbicacion.identificar(0, 0);
        // Medio feo, pero fuerza a que se cargue al inicio
    }

}
