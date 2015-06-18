package pl.kalisz.kamil.preffer.clean;

import java.util.Set;

import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Cleaner implementation that removes only persistent values from {@link Store}
 */
public class PersistentCleaner implements Cleaner
{
    @Override
    public void clean(Store store)
    {
        PersistentStore persistentStore = new PersistentStore(store);
        Set<String> keySet = persistentStore.getKeys();

        for(String key : keySet)
        {
            persistentStore.setValue(key,null);
        }
    }
}
