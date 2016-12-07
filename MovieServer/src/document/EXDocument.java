package document;

import org.bson.Document;

import java.lang.reflect.Field;
import java.util.IllegalFormatException;

/**
 * Created by yaodixing on 2016/12/7.
 */
public class EXDocument{
    private Document document;
    private Object pojo;
    private int i=0;
    public EXDocument(Object pojo){
        this.pojo = pojo;
        i=0;
        bind(pojo,null);
    }

    public Document getDocument(){
        return document;
    }

    Document lastDocument;
    private void bind(Object obj,String name){
        int a = i;
        i++;
        Document d = new Document();
        if(document==null){
            document = new Document();
        }else {
            if(a==1){
                document.append(name,d);
                lastDocument = d;
            }
            if(a >=2){
                lastDocument.append(name,d);
                lastDocument = d;
            }
        }

        Class clazz=null;
        if(obj!=null) {
            clazz = obj.getClass();
        }else {
            return;
        }
       Field [] fields = clazz.getDeclaredFields();
        if(fields!=null)
            for(Field field:fields){
                try {
                    field.setAccessible(true);
                    String fieldName = field.getType().getName();
                      //String
                    if(fieldName.equals(String.class.getName())){
                        if(a>0)
                            d.append(field.getName(),field.get(obj)==null?"":field.get(obj).toString());
                        else
                            document.append(field.getName(),field.get(obj)==null?"":field.get(obj).toString());
                    }
                    else if(fieldName.equals(Integer.class.getName())||fieldName.equals("int")){
                        if(a>0)
                            d.append(field.getName(),field.getInt(obj));
                        else
                            document.append(field.getName(),field.getInt(obj));
                    }
                    else if(fieldName.equals(Boolean.class.getName())||fieldName.equals("boolean")){
                        if(a>0)
                            d.append(field.getName(),field.getBoolean(obj));
                        else
                            document.append(field.getName(),field.getBoolean(obj));
                    }
                    else if(fieldName.equals(Byte.class.getName())||fieldName.equals("byte")){
                        if(a>0)
                        d.append(field.getName(),field.getByte(obj));
                        else
                            document.append(field.getName(),field.getByte(obj));
                    }
                    else if(fieldName.equals(Character.class.getName())||fieldName.equals("char")){
                        if(a>0)
                        d.append(field.getName(),field.getChar(obj));
                        else
                            document.append(field.getName(), field.getChar(obj));
                    }
                    else if(fieldName.equals(Long.class.getName())||fieldName.equals("long")){
                        if(a>0)
                        d.append(field.getName(),field.getLong(obj));
                        else
                            document.append(field.getName(),field.getLong(obj));
                    }
                    else if(fieldName.equals(Double.class.getName())||fieldName.equals("double")){
                        if(a>0)
                        d.append(field.getName(),field.getDouble(obj));
                        else
                            document.append(field.getName(),field.getDouble(obj));
                    }
                    else if(fieldName.equals(Float.class.getName())||fieldName.equals("float")){
                        if(a>0)
                        d.append(field.getName(),field.getFloat(obj));
                        else
                            document.append(field.getName(),field.getFloat(obj));
                    }
                    else if(fieldName.equals(Short.class.getName())||fieldName.equals("short")){
                        if(a>0)
                        d.append(field.getName(),field.getShort(obj));
                        else
                            document.append(field.getName(),field.getShort(obj));
                    }
                    else {
                        bind(field.get(obj),field.getName());
                    }
                }   catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
    }
}
