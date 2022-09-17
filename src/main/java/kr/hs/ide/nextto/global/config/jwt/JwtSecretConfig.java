package kr.hs.ide.nextto.global.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtSecretConfig {
    private String accessSecret;
    private String refreshSecret;
}
