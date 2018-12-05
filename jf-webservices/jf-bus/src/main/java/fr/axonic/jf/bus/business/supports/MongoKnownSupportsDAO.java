package fr.axonic.jf.bus.business.supports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import fr.axonic.jf.dao.JerseyMapperProvider;
import fr.axonic.jf.engine.support.Support;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * TODO Make better use of the fact that the Supports are easily translatable into JSON.
 */
public class MongoKnownSupportsDAO implements KnownSupportsDAO {

    private static final ObjectMapper MAPPER = new JerseyMapperProvider().getContext(null);
    private static final String CONTENT_FIELD = "content";
    private static final String KNOWN_SUPPORTS_DATABASE_URL =
            Optional.ofNullable(System.getenv("ksDatabaseUrl")).orElse("mongodb://localhost:27017");
    private static final String KNOWN_SUPPORTS_DATABASE_NAME =
            Optional.ofNullable(System.getenv("ksDatabaseName")).orElse("jf");
    private static final String KNOWN_SUPPORTS_COLLECTION =
            Optional.ofNullable(System.getenv("ksDatabaseCollection")).orElse("knownSupports");

    private final String url;
    private final String databaseName;
    private final String collectionName;
    private MongoClient client;

    public MongoKnownSupportsDAO() {
        this(KNOWN_SUPPORTS_DATABASE_URL, KNOWN_SUPPORTS_DATABASE_NAME, KNOWN_SUPPORTS_COLLECTION);
    }

    public MongoKnownSupportsDAO(String url, String databaseName, String collectionName) {
        this.url = url;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public void saveSupport(Support support) throws IOException {
        if (client == null) {
            client = makeMongoClient();
        }

        MongoCollection<Document> collection = fetchCollection(client);

        collection.insertOne(new Document().append(CONTENT_FIELD, MAPPER.writeValueAsString(support)));
    }

    @Override
    public List<Support> loadSupports() throws IOException {
        List<Support> temporarySupports = new ArrayList<>();

        if (client == null) {
            client = makeMongoClient();
        }

        MongoCollection<Document> collection = fetchCollection(client);

        for (Document foundDocument : collection.find()) {
            temporarySupports.add(MAPPER.readValue(foundDocument.getString(CONTENT_FIELD), Support.class));
        }

        return Collections.unmodifiableList(temporarySupports);
    }

    @Override
    public void removeAllSupports(List<Support> toBeRemoved) throws IOException {
        if (client == null) {
            client = makeMongoClient();
        }

        MongoCollection<Document> collection = fetchCollection(client);

        for (Support support : toBeRemoved) {
            Document associatedDocument = new Document().append(CONTENT_FIELD, MAPPER.writeValueAsString(support));

            collection.deleteMany(associatedDocument);
        }
    }

    @Override
    public void makeEmpty() {
        if (client == null) {
            client = makeMongoClient();
        }

        MongoCollection<Document> collection = fetchCollection(client);

        collection.drop();
    }

    private MongoClient makeMongoClient() {
        return MongoClients.create(new ConnectionString(url));
    }

    private MongoCollection<Document> fetchCollection(MongoClient client) {
        return client.getDatabase(databaseName).getCollection(collectionName);
    }

    protected void finalize() {
        if (client != null) {
            client.close();
        }
    }
}
