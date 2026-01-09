package cat.itacademyS5_01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "cat.itacademyS5_01.player.repository")
public class R2dbcConfig {
}