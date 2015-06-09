package pl.kalisz.kamil.preffer.serilizer;

/**
 * Interface for serialization and deserialization objects from and to String
 */
public interface Serializer
{
    /**
     * @param type of object to serialize
     * @param objectToSerialize object to serialize
     * @param <V> type of objects
     * @return serialized object
     */
    <V> String serialize(Class<V> type, V objectToSerialize);

    /***
     * @param type of object to serialize
     * @param objectToDeSerialize string to deserialize to object
     * @param <V> type of objects
     * @return deserialized object
     */
    <V> V deserialize(Class<V> type, String objectToDeSerialize);
}
