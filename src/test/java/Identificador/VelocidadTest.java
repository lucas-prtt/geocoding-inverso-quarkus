package Identificador;

import decoder.IdentificadorDeUbicacion;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTimeout;

@QuarkusTest
public class VelocidadTest {

    @Inject
    IdentificadorDeUbicacion identificadorDeUbicacion;
    private static final int ITERACIONES = 1_000;
    @Test
    public void decodeUnder1ms() {
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
