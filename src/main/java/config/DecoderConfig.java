package config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "decoder")
public interface DecoderConfig {
    boolean useCache();
    boolean forceBuildCache();
}
