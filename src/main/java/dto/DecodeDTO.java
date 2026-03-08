package dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import config.ControllerConfig;
import decoder.Provincia;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DecodeDTO {

    private final String iso;
    private final String provincia;
    private final String pais;

    public DecodeDTO(String iso, String provincia, String pais) {
        this.iso = iso;
        this.provincia = provincia;
        this.pais = pais;
    }
    public DecodeDTO(String iso, String provincia, String pais, ControllerConfig controllerConfig) {
        this.iso = controllerConfig.decode().includeIso() ? iso : null;
        this.pais = controllerConfig.decode().includeCountry() ? pais : null;
        this.provincia = controllerConfig.decode().includeProvince() ? provincia : null;
    }
    public DecodeDTO(Provincia provincia, ControllerConfig controllerConfig) {
        this.iso = controllerConfig.decode().includeIso() ? provincia.getIso() : null;
        this.pais = controllerConfig.decode().includeCountry() ? provincia.getPais() : null;
        this.provincia = controllerConfig.decode().includeProvince() ? provincia.getProvincia() : null;
    }
}
