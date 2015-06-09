package pl.kalisz.kamil.preffer.serilizer;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

/**
 * Implementation os Serializer that uses Json to Store objects
 */
public class JsonSerializer implements Serializer {
    @Override
    public <V> String serialize(Class<V> type, V objectToSerialize) {
        if (objectToSerialize == null) {
            return null;
        }
        return JsonWriter.objectToJson(objectToSerialize);
    }

    @Override
    public <V> V deserialize(Class<V> type, String objectToDeSerialize) {
        if (objectToDeSerialize == null) {
            return null;
        }
        return (V) JsonReader.jsonToJava(objectToDeSerialize);

    }
}
