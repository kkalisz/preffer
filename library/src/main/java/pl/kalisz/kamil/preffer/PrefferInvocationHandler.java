package pl.kalisz.kamil.preffer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
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

    public PrefferInvocationHandler(Store delegate, String profile) {
        this.profile = profile;
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        SaveValueHelper saveValueHelper = new SaveValueHelper(method);

        Store saveStore = delegate;
        SaveValue saveValueAnnotation = saveValueHelper.getAnnotation();
        if(saveValueAnnotation.persistent())
        {
            saveStore = new PersistentStore(saveStore);
        }
        if (saveValueAnnotation.profile()) {
            saveStore = new ProfileStore(saveStore, profile);
        }
        String key = saveValueAnnotation.key();
        AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
        Class accesClass = accessTypeHolder.getAccessValueClass();
        switch (accessTypeHolder.getAccessType()) {
            case GET:
                //TODO conversion
                return saveStore.getValue(key);
                 //TODO conversion
            case SET:
                saveStore.setValue(key, args[0].toString());
                break;

        }

        return null;

    }
}
