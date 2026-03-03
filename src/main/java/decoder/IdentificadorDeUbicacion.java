package decoder;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.geom.Envelope;
import utils.CodeTimer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;




/*
 *
 * https://gadm.org/data.html
 *
 * Archivos geoJson de varios paises
 *
 * https://mapshaper.org/
 *
 * Sitio para reducir tamaño de los mapas
 *
 * Actualmente, con solo las provincias argentinas guardadas a la resolución por defecto, puede identificar 1.000.000 de provincias en menos de 200 ms
 * No deberia dar problemas de rendimiento, siempre y cuando no le demos demasiados datasets de provincias
 */





public class IdentificadorDeUbicacion {

    private static IdentificadorDeUbicacion instancia;
    private final List<Provincia> provincias = new ArrayList<>();
    private final STRtree spatialIndex = new STRtree();
    private final Provincia defaultProvincia = new Provincia(null, "Desconocida", "Desconocido", "XX");
    // Lista de provincias en memoria
    private final GeometryFactory gf = new GeometryFactory();
    // Factory necesario para crear puntos y evaluarlos

    public static IdentificadorDeUbicacion getInstance() {
        if (instancia == null) {
        instancia = new IdentificadorDeUbicacion();
        }
        return instancia;
    }

    public static IdentificadorDeUbicacion getSeparateInstance(){
        return new IdentificadorDeUbicacion();
    }
    private IdentificadorDeUbicacion() {
        CodeTimer timer = new CodeTimer();
        timer.start();
        String geoJsonContent = readGeoJsonFromResources("provincias.geojson");
        timer.stop();
        timer.printTime("Leer Json");
        // Lee el geoJson
        timer.start();
        GeoJSON gj = GeoJSONFactory.create(geoJsonContent);
        timer.stop();
        timer.printTime("Crear Features");
        // Crea un geoJson
        if (!(gj instanceof FeatureCollection fc)) {
            throw new IllegalArgumentException("Esperaba un FeatureCollection de provincias");
        }

        //Verifica que sea un "FeatureCollection"
        GeoJSONReader reader = new GeoJSONReader();
        int idx = 0;
        for (Feature feature : fc.getFeatures()) {
            // Por cada "feature" (provincia)
            Geometry geomJts;

            org.wololo.geojson.Geometry ggeom = feature.getGeometry();
            if (ggeom == null) {
                System.err.println("Feature index " + idx + " tiene geometría nula. Se ignora.");
                idx++;
                continue;
            }

            geomJts = reader.read(ggeom);
            // La transforma a geometria de JTS, para poder usar contains()
            String provincia = (String) feature.getProperties().get("NAME_1");
            String pais = (String) feature.getProperties().get("COUNTRY");
            String iso = (String) feature.getProperties().get("ISO_1");
            // Busca el nombre de la provincia
            Provincia insertProv = new Provincia(geomJts, provincia, pais, iso);

            provincias.add(insertProv);
            insertProv.buildPreparedGeometry();
            // Guarda la provincia, sus nombres y geometria convertida a memoria
            spatialIndex.insert(
                    insertProv.getGeometry().getEnvelopeInternal(), insertProv
            );
        }

        spatialIndex.build();
    }

    private static String readGeoJsonFromResources(String fileName) {
        InputStream inputStream = IdentificadorDeUbicacion.class.getClassLoader().getResourceAsStream(fileName);
        //Saca el geoJson de resources
        if (inputStream == null) {
            throw new RuntimeException("Archivo no encontrado en resources: " + fileName);
        }
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        }
        // Convierte el geoJson a un string
    }

    public Provincia identificar(double latitud, double longitud) {
        Point punto = gf.createPoint(new Coordinate(longitud, latitud));
        Envelope searchEnv = punto.getEnvelopeInternal();
        searchEnv.expandBy(0.5); // 55km en grados
        @SuppressWarnings("unchecked")
            List<Provincia> candidatas =
                    spatialIndex.query(searchEnv);

            for (Provincia provincia : candidatas) {
                if (provincia.getPreparedGeom().contains(punto)) {
                    return provincia;
                }
            }
            Provincia provinciaMasCercana = null;
            double distanciaMinima = Double.MAX_VALUE;
            double distanciaMaximaGrados = 0.5; // ~55 km en grados

            for (Provincia provincia : candidatas) {
                double distancia = provincia.getGeometry().distance(punto);
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    provinciaMasCercana = provincia;
                }
            }

            if (provinciaMasCercana != null && distanciaMinima <= distanciaMaximaGrados) {
                return provinciaMasCercana;
            }
            return defaultProvincia;
    }
}