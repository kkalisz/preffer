package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

/**
 * Builder class for creating #CleanerObject#
 */
public class CleanerBuilder<V>
{
    private boolean cleanPersistent;

    private boolean cleanProfile;

    private boolean cleanNormal;

    private Class<V> cleanClass;

    /**
     * @param cleanPersistent true if #cleaner# instance should also clean values with {@link SaveValue#persistent()} set true
     * @return builder instance
     */
    public CleanerBuilder cleanPersistent(boolean cleanPersistent) {
        this.cleanPersistent = cleanPersistent;
        return this;
    }

    /**
     * @param cleanProfile true if #cleaner# instance should also clean values with {@link SaveValue#profile()} set true
     * @return builder instance
     */
    public CleanerBuilder cleanProfile(boolean cleanProfile) {
        this.cleanProfile = cleanProfile;
        return this;
    }

    /**
     * @param cleanNormal true if #clean# instance should clean values that are not mark as {@link SaveValue#profile()} or {@link SaveValue#persistent()}
     * @return builder instance
     */
    public CleanerBuilder cleanNormal(boolean cleanNormal)
    {
        this.cleanNormal = cleanNormal;
        return this;
    }

    /**
     * @param cleanClass preferences class on which values cleaning should take place, if class is null or is not set values of all preferences will be cleaned according to other properties
     * @return builder instance
     */
    public CleanerBuilder<V> cleanClass(Class<V> cleanClass) {
        this.cleanClass = cleanClass;
        return this;
    }

    /**
     * method set builder parameters that #cleaner# will remove all saved entries
     * @return builder instance
     */
    public CleanerBuilder cleanAll()
    {
        return cleanClass(null).cleanNormal(true).cleanPersistent(true).cleanProfile(true);
    }

    public boolean isCleanPersistent() {
        return cleanPersistent;
    }

    public boolean isCleanProfile() {
        return cleanProfile;
    }

    public boolean isCleanNormal() {
        return cleanNormal;
    }

    public Class<V> getCleanClass() {
        return cleanClass;
    }
}
