package pl.kalisz.kamil.preffer.store;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Utils for managing {@link ContextKey}
 */
public class ContextKeyUtils
{
    /**
     * @param keysToFilter list of keys to filter
     * @param context to filter keys
     * @return filtered subset of keys
     */
    public static Set<ContextKey> filterByContext(Collection<String> keysToFilter, String context)
    {
        Set<ContextKey> contextKeys = new HashSet<>();

        for(String potentialContextKey : keysToFilter)
        {
            try
            {
                ContextKey contextKey = ContextKey.fromContextKeyString(potentialContextKey);
                if(contextKey.getContext().equals(context))
                {
                    contextKeys.add(contextKey);
                }
            }
            catch (IllegalArgumentException e)
            {
                // we do nothing it's normal that not all keys are ContextKeys
            }
        }

        return contextKeys;
    }

    /**
     * @param contextKeys collection of ContextKey to extract raw keys
     * @return set of raw keys extracted from ContextKeys
     */
    public static Set<String> getRawKeys(Collection<ContextKey> contextKeys)
    {
        Set<String> rawKeys  = new HashSet<>();
        for(ContextKey contextKey : contextKeys)
        {
            rawKeys.add(contextKey.getRawKey());
        }
        return rawKeys;
    }
}
