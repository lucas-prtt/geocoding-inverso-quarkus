package config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "controller")
public interface ControllerConfig {
    Decode decode();
    public interface Decode{
        boolean includeIso();
        boolean includeCountry();
        boolean includeProvince();
    }
}
