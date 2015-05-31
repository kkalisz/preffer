package pl.kalisz.kamil.preffer.clean;

import java.util.Set;

import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Cleaner implementation that removes only non persistent values from {@link Store}
 */
public class NonPersistentCleaner implements Cleaner {
    @Override
    public void clean(Store store) {
        PersistentStore persistentStore = new PersistentStore(store);
        Set<String> globalKeySet = store.getKeys();
        Set<String> persistentKeySet = persistentStore.getKeys();

        for (String key : globalKeySet) {
            if (!persistentKeySet.contains(key)) {
                store.setValue(key, null);
            }
        }
    }
}
