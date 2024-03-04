package org.example;

/**
 * Hello world!
 *
 */
import org.bson.Document;

import java.util.List;

public class App {
    static String dbName = "users";

    public static void main(String[] args) {

        MongoDBUtility mongoDBUtility = new MongoDBUtility("app");

        // Adding a document
        Document newDoc = new Document("name", "John Doe").append("age", 30);
        mongoDBUtility.addDocument("users", newDoc);

        // Select a document
        List<Document> foundDocs = mongoDBUtility.selectDocuments("users", "name", "John Doe");
        for (Document doc : foundDocs) {
            System.out.println(doc.toJson());
        }

        // Updating a document
        mongoDBUtility.updateDocument("users", "name", "John Doe", "age", "31");

        // Removing a document
        mongoDBUtility.removeDocument("users", "name", "John Doe");
    }
}

