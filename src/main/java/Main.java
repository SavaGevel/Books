import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import javax.print.Doc;
import java.util.function.Consumer;


public class Main {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("127.0.0.1" , 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("local");

        MongoCollection<Document> books = mongoDatabase.getCollection("books");

        books.drop();

        books.insertOne(new Document().append("name", "Преступление и наказание")
                .append("author", "Фёдор Михайлович Достоевский")
                .append("year", 1866));

        books.insertOne(new Document().append("name", "Идиот")
                .append("author", "Фёдор Михайлович Достоевский")
                .append("year", 1868));

        books.insertOne(new Document().append("name", "Белая гвардия")
                .append("author", "Михайил Афанасьевич Булгаков")
                .append("year", 1925));

        books.insertOne(new Document().append("name", "Лезвие бритвы")
                .append("author", "Иван Антонович Ефремов")
                .append("year", 1963));

        books.insertOne(new Document().append("name", "Жизнь взаймы")
                .append("author", "Эрих Мария Ремарк")
                .append("year", 1959));

        BsonDocument theOldestBookQuery = BsonDocument.parse("{year : 1}");
        System.out.println("Самая старая книга из коллекции: ");
        books.find().sort(theOldestBookQuery).limit(1).forEach((Consumer<Document>) doc -> {
            System.out.println(doc.get("name"));
            System.out.println(doc.get("author"));
            System.out.println(doc.get("year"));
        });

        System.out.println();
        System.out.println("Любимый автор: Фёдор Михайлович Достоевский");
        BsonDocument booksOfLovelyAuthorQuery = BsonDocument.parse("{author : {$eq : \"Фёдор Михайлович Достоевский\"}}");
        books.find(booksOfLovelyAuthorQuery).forEach((Consumer<Document>) doc -> System.out.println(doc.get("name") + " - " + doc.get("year")));

    }

}
