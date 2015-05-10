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

        }

        if(!myPrefferClass.isInterface())
        {

        }


        return (V) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{myPrefferClass}, new PrefferInvocationHandler());
    }

}
