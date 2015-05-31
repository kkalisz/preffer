package pl.kalisz.kamil.preffer.clean;

import pl.kalisz.kamil.preffer.store.Store;

/**
 * interface for removing preferences from {@link Store}
 */
public interface Cleaner
{
    void clean(Store store);
}
