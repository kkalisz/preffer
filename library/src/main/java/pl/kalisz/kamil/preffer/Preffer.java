package pl.kalisz.kamil.preffer;

import java.lang.reflect.Proxy;

/**
 * Main class that handle creation of Preferences
 */
public class Preffer
{


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
        return (V) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{myPrefferClass}, new PrefferInvocationHandler());
    }

}
