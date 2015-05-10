package pl.kalisz.kamil.preffer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.store.ProfileStore;
import pl.kalisz.kamil.preffer.store.SaveValueHelper;
import pl.kalisz.kamil.preffer.store.Store;

/**
 *  Invoation handler that will use invoked method types/parameters to invoke save and load operation on Store
 */
public class PrefferInvocationHandler implements InvocationHandler
{
    private Store delegate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {

        //TODO should be exception throwed when someone invoke method that is not annotated with SaveValue ??
        SaveValueHelper saveValueHelper = SaveValueHelper.buildIfCorrectMethod(method);
        if(saveValueHelper != null)
        {
            saveValueHelper.validate();
            Store saveStore = delegate;
            SaveValue saveValueAnnotation = saveValueHelper.getAnnotation();
            if(saveValueAnnotation.profile())
            {
                saveStore = new ProfileStore(saveStore,"TODO");
                String key = saveValueAnnotation.key();
                Class accesClass = saveValueHelper.getAccesValueClass();
                switch (saveValueHelper.getAccessTypeHolder()) {
                    case SET:
                        //TODO conversion
                        saveStore.getValue(key);
                        break;
                        //TODO conversion
                    case GET:
                        saveStore.setValue(key,null);
                        break;
                }
            }
        }
        return null;

    }
}