package Identificador;

import decoder.IdentificadorDeUbicacion;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class EjecucionTest {
    @Inject
    IdentificadorDeUbicacion identificadorDeUbicacion;

    @Test
    public void decodeByInclusion(){
        assertEquals("Ciudad de Buenos Aires", identificadorDeUbicacion.identificar(-34.606207, -58.472550).getProvincia());
        assertEquals("Argentina", identificadorDeUbicacion.identificar(-34.606207, -58.472550).getPais());
    }
    @Test
    public void decodeByProximity(){
        assertEquals("Buenos Aires", identificadorDeUbicacion.identificar(-35.915476, -57.189670).getProvincia());
        assertEquals("Argentina", identificadorDeUbicacion.identificar(-35.915476, -57.189670).getPais());
    }
    @Test
    public void decodeUnknown(){
        assertEquals("Desconocida", identificadorDeUbicacion.identificar(-35.617782, -56.176996).getProvincia());
        assertEquals("Desconocido", identificadorDeUbicacion.identificar(-35.617782, -56.176996).getPais());
    }
}
