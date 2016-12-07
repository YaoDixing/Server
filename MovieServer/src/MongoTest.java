import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import dao.CollectionsController;
import db.MongoDBJDBC;
import document.EXDocument;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import pojo.M;
import pojo.Movie;

import java.util.List;

/**
 * Created by yaodixing on 2016/12/6.
 */
public class MongoTest {
    public static void main(String[] args){
      init();
    }

    static void init(){
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.C_SHARP_LEGACY)),
                              MongoClient.getDefaultCodecRegistry());
        MongoDatabase database = MongoDBJDBC.conn("localhost", 27017, "video_db",codecRegistry);
        CollectionsController controller = new CollectionsController(database,"movie",codecRegistry);

        Movie movie = new Movie();
        movie.setName("好看.mp4");
        movie.setUrl("http://www.baidu.com");
        movie.setM(new M());
        EXDocument exDocument = new EXDocument(movie);
       controller.insertOne(exDocument.getDocument());
    }
}
