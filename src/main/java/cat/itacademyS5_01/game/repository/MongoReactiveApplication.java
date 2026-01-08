package cat.itacademyS5_01.game.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = {"cat.itacademyS5_01.game.repository", "cat.itacademyS5_01.player.repository"})
public class MongoReactiveApplication extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.host:localhost}")
    private String host;

    @Value("${spring.data.mongodb.port:27017}")
    private int port;

    @Value("${spring.data.mongodb.database:blackjack}")
    private String database;

    @Value("${spring.data.mongodb.username:admin}")
    private String username;

    @Value("${spring.data.mongodb.password:admin}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        String connectionString = String.format(
            "mongodb://%s:%s@%s:%d/%s?authSource=admin",
            username, password, host, port, database
        );
        return MongoClients.create(connectionString);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}

