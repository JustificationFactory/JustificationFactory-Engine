package fr.axonic.jf.dao.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import fr.axonic.jf.dao.JerseyMapperProvider;
import fr.axonic.jf.dao.JustificationSystemsDAO;
import fr.axonic.jf.engine.JustificationSystem;
import fr.axonic.jf.engine.JustificationSystemAPI;
import org.bson.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MongoJustificationSystemsDAO implements JustificationSystemsDAO {

    private static final ObjectMapper MAPPER = new JerseyMapperProvider().getContext(null);
    private static final String NAME_FIELD = "name";
    private static final String SYSTEM_FIELD = "system";
    private static final String JUSTIFICATION_SYSTEMS_DATABASE_URL =
            Optional.ofNullable(System.getenv("jsDatabaseUrl")).orElse("mongodb://localhost:27017");
    private static final String JUSTIFICATION_SYSTEMS_DATABASE_NAME =
            Optional.ofNullable(System.getenv("jsDatabaseName")).orElse("jf");
    private static final String JUSTIFICATION_SYSTEMS_COLLECTION =
            Optional.ofNullable(System.getenv("jsDatabaseCollection")).orElse("justificationSystems");

    private final String url;
    private final String databaseName;
    private final String collectionName;

    public MongoJustificationSystemsDAO() {
        this(JUSTIFICATION_SYSTEMS_DATABASE_URL, JUSTIFICATION_SYSTEMS_DATABASE_NAME, JUSTIFICATION_SYSTEMS_COLLECTION);
    }

    public MongoJustificationSystemsDAO(String url, String databaseName, String collectionName) {
        this.url = url;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public Map<String, JustificationSystem> loadJustificationSystems() throws IOException {
        Map<String, JustificationSystem> systems = new HashMap<>();

        MongoClient client = makeMongoClient();
        MongoCollection<Document> collection = fetchCollection(client);

        try {
            for (Document fetchedDocument : collection.find()) {
                JustificationSystem js = MAPPER.readValue(fetchedDocument.getString(SYSTEM_FIELD), JustificationSystem.class);

                systems.put(fetchedDocument.getString(NAME_FIELD), js);
            }
        } finally {
            client.close();
        }

        return systems;
    }

    @Override
    public void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException {
        Document savedDocument = new Document().append(NAME_FIELD, name)
                .append(SYSTEM_FIELD, MAPPER.writeValueAsString(argumentationSystem));

        MongoClient client = makeMongoClient();
        MongoCollection<Document> collection = fetchCollection(client);

        try {
            collection.replaceOne(new Document().append(NAME_FIELD, name), savedDocument,
                    ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true)));
        } finally {
            client.close();
        }
    }

    @Override
    public void removeJustificationSystem(String name) {
        MongoClient client = makeMongoClient();

        MongoCollection<Document> collection = fetchCollection(client);

        try {
            collection.deleteOne(new Document().append(NAME_FIELD, name));
        } finally {
            client.close();
        }
    }

    private MongoClient makeMongoClient() {
        return MongoClients.create(new ConnectionString(url));
    }

    private MongoCollection<Document> fetchCollection(MongoClient client) {
        return client.getDatabase(databaseName).getCollection(collectionName);
    }
}
