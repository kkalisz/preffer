package pl.kalisz.kamil.preffer.store;

/**
 *  Interface that provides read and save operations, based on key
 */
public interface Store
{
    /**
     * @param key used to unique identify value
     * @param value String representation object to save
     */
    void setValue(String key, String value);

    /**
     * @param key used to unique identify value
     * @return String representation of saved object
     */
    String getValue(String key);
}
