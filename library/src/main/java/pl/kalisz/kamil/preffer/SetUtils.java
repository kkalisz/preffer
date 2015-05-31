package pl.kalisz.kamil.preffer;

import java.util.HashSet;
import java.util.Set;


public class SetUtils
{
    /**
     * @param setToFilter collection to filter
     * @param prefix prefix calue used to filter collection
     * @return filtered Set of values, that starts with prefix
     */
    public static Set<String> filterSetByPrefix(Set<String> setToFilter, String prefix)
    {
        Set<String> filteredKeys = new HashSet<>();
        for(String key : setToFilter)
        {
            if(key != null && key.startsWith(prefix))
            {
                filteredKeys.add(key);
            }
        }
        return filteredKeys;
    }

}
