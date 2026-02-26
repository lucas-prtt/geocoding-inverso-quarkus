package decoder;

import lombok.Getter;
import org.locationtech.jts.geom.Geometry;

@Getter
public class Provincia {
    private final Geometry geom;
    private final String iso;
    private final String provincia;
    private final String pais;

    public Provincia(Geometry geom, String provincia, String pais, String iso) {
        this.geom = geom;
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
                ", geom=" + (geom != null ? geom.getGeometryType() : "null") +
                '}';
    }
    public String toLongString() {
        return iso + ": " + provincia + ", " + pais;
    }
    public String toMediumString(){
        return provincia + ", " + pais;
    }
}