package pl.kalisz.kamil.preffer.store;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.preffer.SetUtils;

/**
 * Store implementation that can handle multi-user
 */
public class ProfileStore implements Store, Serializable
{
    private static final String DELIMITER = "[@]";
    private Store delegate;

    private String profile;

    /**
     * @param saveStore store to be used to save/read values with added information about profile
     * @param profile String representation of current profile
     */
    public ProfileStore(Store saveStore, String profile)
    {
        this.delegate = saveStore;
        this.profile = profile;
    }


    @Override
    public void setValue(String key, String value)
    {
        delegate.setValue(new ContextKey(profile,key).generateContextKey(), value);
    }

    @Override
    public String getValue(String key)
    {
        return delegate.getValue(new ContextKey(profile,key).generateContextKey());
    }

    @Override
    public Set<String> getKeys()
    {
        Set<String> allDelegateKeys = delegate.getKeys();
        Collection<ContextKey> contextKeys = ContextKeyUtils.filterByContext(allDelegateKeys, profile);
        return ContextKeyUtils.getRawKeys(contextKeys);
    }

}
