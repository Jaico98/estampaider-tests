package com.estampaider.integration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MongoConnectionTest {

    @Test
    void testMongoConnection() {
        String connectionString = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            assertNotNull(mongoClient.listDatabaseNames());
            System.out.println("✅ Conexión exitosa a MongoDB");
        } catch (Exception e) {
            fail("❌ Falló la conexión a MongoDB: " + e.getMessage());
        }
    }
}
