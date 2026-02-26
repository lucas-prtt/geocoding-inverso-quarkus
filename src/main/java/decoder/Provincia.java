package decoder;

import lombok.Getter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

@Getter
public class Provincia {
    private final Geometry geometry;
    private final PreparedGeometry preparedGeom;
    private final String iso;
    private final String provincia;
    private final String pais;

    public Provincia(Geometry geom, String provincia, String pais, String iso) {
        this.geometry = geom;
        if(geom != null)
            this.preparedGeom = PreparedGeometryFactory.prepare(geom);
        else
            this.preparedGeom = null;
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
    public String toLongString() {
        return iso + ": " + provincia + ", " + pais;
    }
    public String toMediumString(){
        return provincia + ", " + pais;
    }
}