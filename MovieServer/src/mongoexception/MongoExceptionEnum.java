package mongoexception;

/**
 * Created by yaodixing on 2016/12/7.
 */
public enum MongoExceptionEnum {
    NamespaceExits(48);
    private int code;
    private String errmsg;

    private MongoExceptionEnum(int code){
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
