package pl.kalisz.kamil.preffer.store;

import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.preffer.SetUtils;

/**
 * Store implementation for marking preferences that should be persistent
 */
public class PersistentStore implements Store {
    private static final String DELIMITER = "[$]";

    public static final String PERSISTENT_KEY_PREFIX = "@PERSISTENT@";

    private Store delegate;

    /**
     * @param saveStore store to be used to save/read values with added information about persist
     */
    public PersistentStore(Store saveStore) {
        this.delegate = saveStore;
    }


    @Override
    public void setValue(String key, String value) {
        delegate.setValue(generateProfileKey(key), value);
    }

    @Override
    public String getValue(String key) {
        return delegate.getValue(generateProfileKey(key));
    }

    @Override
    public Set<String> getKeys()
    {
        Set<String> allDelegateKeys = delegate.getKeys();
        String keyPrefix = String.format("%s%s",PERSISTENT_KEY_PREFIX,DELIMITER);
        return SetUtils.filterSetByPrefix(allDelegateKeys,keyPrefix);
    }
    /**
     * @param valueKey original preference key
     * @return key with added information about persistence
     */
    private String generateProfileKey(String valueKey) {
        // FIXME There can be situation that someone use our DELIMITER value as part of key
        return String.format("%s%s%s", PERSISTENT_KEY_PREFIX, DELIMITER, valueKey);
    }
}
