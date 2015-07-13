package pl.kalisz.kamil.preffer.serializer;

import java.util.Objects;

/**
 * Interface for serialization and deserialization objects from and to String
 */
public interface Serializer<V extends Object>
{
    /**
     * @param type of object to serialize
     * @param objectToSerialize object to serialize
     * @return serialized object
     */
    String serialize(Class<V> type, V objectToSerialize);

    /***
     * @param type of object to serialize
     * @param objectToDeSerialize string to deserialize to object
     * @return deserialized object
     */
    V deserialize(Class<V> type, String objectToDeSerialize);
}
