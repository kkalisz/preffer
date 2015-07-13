package pl.kalisz.kamil.preffer.serializer;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.util.Objects;

/**
 * Implementation os Serializer that uses Json to Store objects
 */
@SuppressWarnings("unchecked")
public class JsonSerializer<V> implements Serializer<V>
{

    @Override public String serialize(Class<V> type, V objectToSerialize)
    {
        if (objectToSerialize == null) {
            return null;
        }
        return JsonWriter.objectToJson(objectToSerialize);
    }

    @Override public V deserialize(Class<V> type, String objectToDeSerialize)
    {
        if (objectToDeSerialize == null) {
            return null;
        }
        return (V) JsonReader.jsonToJava(objectToDeSerialize);
    }


}
