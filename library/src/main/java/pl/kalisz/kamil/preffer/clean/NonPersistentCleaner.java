package pl.kalisz.kamil.preffer.clean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.preffer.store.ContextKey;
import pl.kalisz.kamil.preffer.store.ContextKeyUtils;
import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Cleaner implementation that removes only non persistent values from {@link Store}
 */
public class NonPersistentCleaner implements Cleaner {
    @Override
    public void clean(Store store) {
        Set<String> globalKeySet = store.getKeys();
        // w filter only persistent keys
        Collection<ContextKey> persistedContextKeys = ContextKeyUtils.filterByContext(globalKeySet, PersistentStore.PERSISTENT_KEY_PREFIX);
        Set<String> contextKeysAsString = new HashSet<>();
        // we create HashMap of persistent keys as String values
        for(ContextKey contextKey: persistedContextKeys)
        {
            contextKeysAsString.add(contextKey.generateContextKey());
        }

        for (String key : globalKeySet)
        {
            if(!contextKeysAsString.contains(key)) {
                store.setValue(key, null);
            }
        }
    }
}
