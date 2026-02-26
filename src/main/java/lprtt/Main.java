package lprtt;

import decoder.IdentificadorDeUbicacion;

import java.util.Random;

public class Main {
    private static final int ITERACIONES = 1_000_000;

    public static void main(String[] args) {
        long inicioInstancia = System.nanoTime();
        IdentificadorDeUbicacion identificadorDeUbicacion =
                IdentificadorDeUbicacion.getInstance();
        long finInstancia = System.nanoTime();
        long tiempoInstanciaMs = (finInstancia - inicioInstancia) / 1_000_000;
        System.out.println("Tiempo creación instancia: " + tiempoInstanciaMs + " ms");
        Random random = new Random();

        double[] lats = new double[ITERACIONES];
        double[] lons = new double[ITERACIONES];

        for (int i = 0; i < ITERACIONES; i++) {
            lats[i] = -90 + 180 * random.nextDouble();
            lons[i] = -180 + 360 * random.nextDouble();
        }
        long inicioIdentificacion = System.nanoTime();

        for (int i = 0; i < ITERACIONES; i++) {
            identificadorDeUbicacion.identificar(lats[i], lons[i]);
        }
        long finIdentificacion = System.nanoTime();
        long tiempoTotalMs = (finIdentificacion - inicioIdentificacion) / 1_000_000;

        System.out.println("Tiempo total 10.000 identificaciones: " + tiempoTotalMs + " ms");
    }

}