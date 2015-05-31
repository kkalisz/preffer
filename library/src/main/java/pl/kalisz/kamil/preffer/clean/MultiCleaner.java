package pl.kalisz.kamil.preffer.clean;

import java.util.HashSet;
import java.util.Set;

import pl.kalisz.kamil.preffer.store.Store;

/**
 * Wrapper for Collection of {@link Cleaner}
 */
public class MultiCleaner implements Cleaner {

    /**
     * collection of cleaners used in {@link #clean(Store)}
     */
    private Set<Cleaner> cleaners = new HashSet<>();

    /**
     * @param cleaners collection of cleaners used in {@link #clean(Store)}
     */
    public MultiCleaner(Set<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    /**
     * @param cleaner cleaner if it is not added
     */
    public void addCleaner(Cleaner cleaner)
    {
        this.cleaners.add(cleaner);
    }

    /**
     * @param cleaner to be removed
     * @return  true if cleaners have been removed, false otherwise
     */
    public boolean removeCleaner(Cleaner cleaner)
    {
        return cleaners.remove(cleaner);
    }

    @Override
    public void clean(Store store)
    {
        for(Cleaner cleaner : cleaners)
        {
            cleaner.clean(store);
        }
    }
}
