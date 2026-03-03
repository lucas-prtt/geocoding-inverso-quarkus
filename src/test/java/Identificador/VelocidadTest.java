package Identificador;

import decoder.IdentificadorDeUbicacion;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTimeout;

@QuarkusTest
public class VelocidadTest {

    private static final int ITERACIONES = 1_000;
    @Test
    public void loadUnder2s() {
        assertTimeout(Duration.ofSeconds(2), () -> {
            IdentificadorDeUbicacion identificadorDeUbicacion = IdentificadorDeUbicacion.getSeparateInstance();
        });
    }
    @Test
    public void decodeUnder1ms() {
        IdentificadorDeUbicacion identificadorDeUbicacion =
                IdentificadorDeUbicacion.getInstance();
        Random random = new Random();
        double[] lats = new double[ITERACIONES];
        double[] lons = new double[ITERACIONES];
        for (int i = 0; i < ITERACIONES; i++) {
            lats[i] = -90 + 180 * random.nextDouble();
            lons[i] = -180 + 360 * random.nextDouble();
        }
        assertTimeout(Duration.ofMillis(ITERACIONES), ()-> {
            for (int i = 0; i < ITERACIONES; i++) {
                identificadorDeUbicacion.identificar(lats[i], lons[i]);
            }
        });
    }
}
