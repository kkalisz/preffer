package pl.kalisz.kamil.preffer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
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

        //TODO should be exception throwed when someone invoke method that is not annotated with SaveValue ??
        SaveValueHelper saveValueHelper = new SaveValueHelper(method);

        Store saveStore = delegate;
        SaveValue saveValueAnnotation = saveValueHelper.getAnnotation();
        if (saveValueAnnotation.profile()) {
            saveStore = new ProfileStore(saveStore, profile);
        }
        String key = saveValueAnnotation.key();
        AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
        Class accesClass = accessTypeHolder.getAccessValueClass();
        switch (accessTypeHolder.getAccessType()) {
            case SET:
                //TODO conversion
                saveStore.getValue(key);
                break;
            //TODO conversion
            case GET:
                saveStore.setValue(key, null);
                break;

        }

        return null;

    }
}
