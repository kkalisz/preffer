package pl.kalisz.kamil.preffer;

import java.lang.reflect.Proxy;

import pl.kalisz.kamil.preffer.store.Store;

/**
 * Main class that handle creation of Preferences
 */
public class Preffer
{


    private Store store;

    private String profile;

    /**
     * @param myPrefferClass  class of interface of preferences to create
     * @param <V> type of preference class
     * @return instantiated myPrefferClass
     */
    public <V> V preffer(Class<V> myPrefferClass)
    {
        if(myPrefferClass == null)
        {
            throw new NullPointerException("You can't create preferences from null class");
        }

        if(!myPrefferClass.isInterface())
        {
            throw new IllegalArgumentException("You can create preferences only from interface");
        }
        PrefferInvocationHandler invocationHandler = new PrefferInvocationHandler(store, profile);
        return (V) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{myPrefferClass}, invocationHandler);
    }

    public void setStore(Store store)
    {
        this.store = store;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }


}
