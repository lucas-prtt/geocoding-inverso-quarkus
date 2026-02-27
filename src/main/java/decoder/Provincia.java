package decoder;

import lombok.Getter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;


public class Provincia {
    public Geometry getGeometry() {
        return geometry;
    }

    public String getIso() {
        return iso;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }

    private final Geometry geometry;
    private PreparedGeometry preparedGeom;
    private final String iso;
    private final String provincia;
    private final String pais;

    public Provincia(Geometry geom, String provincia, String pais, String iso) {
        this.geometry = geom;
        this.provincia = provincia;
        this.pais = pais;
        this.iso = iso;
    }

    @Override
    public String toString() {
        return "Provincia{" +
                "iso='" + iso + '\'' +
                ", provincia='" + provincia + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
    public void buildPreparedGeometry(){
        if(this.geometry != null)
            this.preparedGeom = PreparedGeometryFactory.prepare(this.geometry);
        else
            this.preparedGeom = null;

    }
    public String toLongString() {
        return iso + ": " + provincia + ", " + pais;
    }
    public String toMediumString(){
        return provincia + ", " + pais;
    }

    public PreparedGeometry getPreparedGeom() {
        return preparedGeom;
    }
}