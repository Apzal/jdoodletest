package base;

import java.util.HashMap;

public class DataContext {
    HashMap<String, Object> data = new HashMap<>();

    public void addToDictionary(String key,Object value){
        data.put(key, value);
    }
    public Object readFromDictionary(String key){
        return data.get(key);
    }

    public void clearDictionary(){
        data.clear();
    }

}
