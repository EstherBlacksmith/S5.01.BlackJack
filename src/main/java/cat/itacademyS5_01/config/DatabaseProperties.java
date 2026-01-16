package cat.itacademyS5_01.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
public class DatabaseProperties {
    private String url;
    private String username;
    private String password;

    public DatabaseProperties() {
    }


}
