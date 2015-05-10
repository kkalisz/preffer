package pl.kalisz.kamil.preffer.store;

import java.io.Serializable;

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
        delegate.setValue(generateProfileKey(key), value);
    }

    @Override
    public String getValue(String key)
    {
        return delegate.getValue(generateProfileKey(key));
    }

    /**
     * @param valueKey original preference key
     * @return key with added information about profile
     */
    private String generateProfileKey(String valueKey)
    {
        // FIXME There can be situation that someone use our DELIMITER value as part of key or profile
        return String.format("%s%s%s",profile, DELIMITER,valueKey);
    }
}
