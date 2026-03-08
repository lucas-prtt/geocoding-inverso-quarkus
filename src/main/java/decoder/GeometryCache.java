package decoder;

import jakarta.enterprise.context.ApplicationScoped;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class GeometryCache {
    private static final String CACHE_FILE = "provincias.cache";

    public boolean cacheExists() {
        return new File(CACHE_FILE).exists();
    }

    public List<Provincia> readProvincias() {

        List<Provincia> provincias = new ArrayList<>();
        WKBReader reader = new WKBReader();

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(CACHE_FILE)))) {

            while (in.available() > 0) {
                int len = in.readInt();
                byte[] wkb = new byte[len];
                in.readFully(wkb);
                Geometry geom = reader.read(wkb);

                String provincia = in.readUTF();
                String pais = in.readUTF();
                String iso = in.readUTF();

                Provincia p = new Provincia(geom, provincia, pais, iso);
                p.buildPreparedGeometry();

                provincias.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo cache de geometría", e);
        }

        return provincias;
    }

    public void writeProvincias(List<Provincia> provincias) {
        WKBWriter writer = new WKBWriter();
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(CACHE_FILE)))) {
            for (Provincia p : provincias) {
                byte[] wkb = writer.write(p.getGeometry());
                out.writeInt(wkb.length);
                out.write(wkb);

                out.writeUTF(p.getProvincia());
                out.writeUTF(p.getPais());
                out.writeUTF(p.getIso());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error escribiendo cache de geometría", e);
        }
    }
}
