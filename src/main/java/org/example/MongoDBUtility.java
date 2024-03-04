package org.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class MongoDBUtility {
    private MongoDatabase database;

    public MongoDBUtility(String dbName) {
        // Use environment variable or system property for MongoDB connection string
        String connectionString = System.getenv("MONGODB_CONNECTION_STRING");
        if (connectionString == null || connectionString.isEmpty()) {
            connectionString = System.getProperty("mongodb.connectionString", "mongodb://root:example@localhost:27017/?authSource=admin");
        }
        initDB(dbName, connectionString);
    }
    public MongoDBUtility(String dbName, String connectionString){
        initDB(dbName, connectionString);
    }

    public void initDB(String dbName, String connectionString) {
        var mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(dbName);
    }

    public List<Document> selectDocuments(String collectionName, String key, String value) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        FindIterable<Document> foundDocuments = collection.find(eq(key, value));
        List<Document> documents = new ArrayList<>();
        for (Document doc : foundDocuments) {
            documents.add(doc);
        }
        return documents;
    }

    public void addDocument(String collectionName, Document document) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertOne(document);
    }

    public void removeDocument(String collectionName, String key, String value) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.deleteOne(eq(key, value));
    }

    public void updateDocument(String collectionName, String identifyingKey, String identifyingValue, String updateKey, String updateValue) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.updateOne(eq(identifyingKey, identifyingValue), set(updateKey, updateValue));
    }
}
