package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.serializer.Serializer;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Class encapsulates acces functionality for methods.
 */
public class Accessor {

    private final AccessTypeHolder accessTypeHolder;
    private final Serializer serializer;
    private final Store store;
    private final SaveValue annotation;

    Accessor(AccessTypeHolder accessTypeHolder, Store store, Serializer serializer, SaveValue annotation) {
        this.store = store;
        this.accessTypeHolder = accessTypeHolder;
        this.serializer = serializer;
        this.annotation = annotation;
    }

    private boolean hasDefaultValue() {
        return accessTypeHolder.hasDefaultValue();
    }

    private Class getValueClass() {
        return accessTypeHolder.getAccessValueClass();
    }

    private String getEntryKey() {
        return annotation.key();
    }

    /**
     * @param methodArguments arguments passed to invoked method
     * @return result of accessing method, it will return deserialized stored value for GET_METHOD, or it will serialize and save value for SET_METHOD
     */
    public Object access(Object[] methodArguments) {
        if (accessTypeHolder.getAccessType() == AccessTypeHolder.AccessType.GET) {
            String valueToDeserialize = store.getValue(getEntryKey());
            Object returnObject = serializer.deserialize(getValueClass(), valueToDeserialize);
            return returnValueOrDefaultIfPresent(returnObject, methodArguments, hasDefaultValue());
        } else {
            String serializedValue = serializer.serialize(getValueClass(), methodArguments[0]);
            store.setValue(getEntryKey(), serializedValue);
            return null;
        }
    }

    /**
     * @param deserializedObject deserialized preference value
     * @param methodArguments arguments of method used to get default preference value
     * @return return if deserializedObject value is null and defaultValue is not null, return defaultValue else return deserializedObject
     */
    private Object returnValueOrDefaultIfPresent(Object deserializedObject, Object[] methodArguments, boolean hasDefaultValue) {
        if (deserializedObject == null && hasDefaultValue) {
            deserializedObject = methodArguments[0];
        }
        return deserializedObject;
    }
}