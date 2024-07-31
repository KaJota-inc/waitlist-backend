package com.kajota.kajota_webpage.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableMongoRepositories(basePackages
        = "com.kajota.kajota_webpage.repositories")
public class MongoConfig {
//    private final Dotenv dotenv = Dotenv.load();
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
//        String mongoUri = dotenv.get("MONGO_URI");
        if (mongoUri == null || mongoUri.isEmpty()) {
            throw new IllegalArgumentException("MONGO_URI environment variable is not set");
        }
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate()
    {
        return new MongoTemplate(mongoClient(),
                "kajota");
    }
}