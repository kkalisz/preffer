package pl.kalisz.kamil.preffer.clean;

import pl.kalisz.kamil.preffer.store.Store;


/**
 * Cleaner implementation that removes all values from {@link Store}
 */
public class AllValuesCleaner implements Cleaner
{
	@Override
	public void clean(Store store)
	{
		for(String key : store.getKeys())
		{
			store.setValue(key,null);
		}
	}
}
