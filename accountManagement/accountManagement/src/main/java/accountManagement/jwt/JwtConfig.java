package accountManagement.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import lombok.Data;


@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Data
public class JwtConfig {
	
	private String secretKey;
    private String tokenPrefix;
    
    @Value("${application.jwt.expires.in}")
    private Long expiresIn;

}
