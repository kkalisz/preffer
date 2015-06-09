package pl.kalisz.kamil.preffer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.serilizer.JsonSerializer;
import pl.kalisz.kamil.preffer.serilizer.Serializer;
import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.ProfileStore;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Invoation handler that will use invoked method types/parameters to invoke save and load operation on Store
 */
public class PrefferInvocationHandler implements InvocationHandler
{
    private Store delegate;

    private String profile;

    private Serializer serializer = new JsonSerializer();

    public PrefferInvocationHandler(Store delegate, String profile,Serializer serializer) {
        this.profile = profile;
        this.delegate = delegate;
        this.serializer = serializer;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        SaveValueHelper saveValueHelper = new SaveValueHelper(method);

        Store saveStore = delegate;
        SaveValue saveValueAnnotation = saveValueHelper.getAnnotation();
        if (saveValueAnnotation.profile()) {
            saveStore = new ProfileStore(saveStore, profile);
        }
        if(saveValueAnnotation.persistent())
        {
            saveStore = new PersistentStore(saveStore);
        }
        String key = saveValueAnnotation.key();
        AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
        Class accesClass = accessTypeHolder.getAccessValueClass();
        switch (accessTypeHolder.getAccessType()) {
            case GET:
                return serializer.deserialize(accesClass, saveStore.getValue(key));
            case SET:
                saveStore.setValue(key, serializer.serialize(accesClass,args[0]));
                break;

        }

        return null;

    }
}
