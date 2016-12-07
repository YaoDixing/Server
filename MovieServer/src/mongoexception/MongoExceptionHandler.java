package mongoexception;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;

/**
 * Created by yaodixing on 2016/12/7.
 */
public class MongoExceptionHandler {
    public static final String ERRMSG="errmsg";
    public static final String CODE="code";
    public static final String CODENAME="codeName";

    public static void handlerMongoException(MongoException e){
        e.printStackTrace();
        switch (e.getCode()){
            case 48:
                System.err.println("collection already exists");
                break;
        }
    }
}
