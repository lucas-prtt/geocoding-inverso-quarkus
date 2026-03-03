package lprtt;

import decoder.IdentificadorDeUbicacion;
import io.netty.handler.logging.LogLevel;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;

import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
public class RutinaInicial {
    private static final Logger LOGGER = Logger.getLogger("RutinaInicial");

    @PostConstruct
    void init() {
        LOGGER.info("Cargando archivo de ubicaciones");
        long inicio = System.nanoTime();
        IdentificadorDeUbicacion.getInstance();
        long fin = System.nanoTime();
        long tiempoMs = (fin - inicio) / 1_000_000;
        LOGGER.info(String.format("Archivo de ubicaciones cargado en %d ms", tiempoMs));
    }

}
