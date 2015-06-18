package pl.kalisz.kamil.preffer;

import java.lang.reflect.Proxy;

import pl.kalisz.kamil.preffer.serializer.JsonSerializer;
import pl.kalisz.kamil.preffer.serializer.Serializer;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Main class that handle creation of Preferences
 */
public class Preffer {

    private final Store store;

    private final String profile;

    private final Serializer serializer;

    /**
     * @param store that will store our preferences
     */
    public Preffer(Store store) {
        this(store, new JsonSerializer());
    }

    /**
     * @param store that will store our preferences
     * @param serializer that will handle serialization and deserialization of preference parameters
     */
    public Preffer(Store store, Serializer serializer) {
        this(store, serializer, null);
    }

    public Preffer(Store store, String profile) {
        this(store, new JsonSerializer(), profile);
    }

    public Preffer(Store store, Serializer serializer, String profile) {
        this.store = store;
        this.profile = profile;
        this.serializer = serializer;
    }

    /**
     * @deprecated use {@link #get(Class)} using method named same as class is not good idea
     * @param myPrefferClass class of interface of preferences to create
     * @param <V>            type of preference class
     * @return instantiated myPrefferClass
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public <V> V preffer(Class<V> myPrefferClass) {
        return get(myPrefferClass);
    }

    /**
     * @param myPrefferClass class of interface of preferences to create
     * @param <V>            type of preference class
     * @return instantiated myPrefferClass
     */
    @SuppressWarnings("unchecked")
    public <V> V get(Class<V> myPrefferClass) {
        if (myPrefferClass == null) {
            throw new NullPointerException("You can't create preferences from null class");
        }

        if (!myPrefferClass.isInterface()) {
            throw new IllegalArgumentException("You can create preferences only from interface");
        }
        PrefferInvocationHandler invocationHandler = new PrefferInvocationHandler(store, profile, serializer);
        return (V) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{myPrefferClass}, invocationHandler);
    }


}
