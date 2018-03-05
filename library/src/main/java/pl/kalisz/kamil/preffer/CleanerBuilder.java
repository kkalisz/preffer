package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.clean.Cleaner;
import pl.kalisz.kamil.preffer.clean.MultiCleaner;
import pl.kalisz.kamil.preffer.clean.NonPersistentCleaner;
import pl.kalisz.kamil.preffer.clean.PersistentCleaner;

import java.util.HashSet;
import java.util.Set;

/**
 * Builder class for creating #CleanerObject#
 */
public class CleanerBuilder
{
	private boolean cleanPersistent;

	private boolean cleanProfile;

	private boolean cleanNormal;

	private Set<Class> cleanClass = new HashSet<>();

	/**
	 * @param cleanPersistent true if #cleaner# instance should also clean values with {@link SaveValue#persistent()} set true
	 * @return builder instance
	 */
	public CleanerBuilder cleanPersistent(boolean cleanPersistent)
	{
		this.cleanPersistent = cleanPersistent;
		return this;
	}

	/**
	 * @param cleanProfile true if #cleaner# instance should also clean values with {@link SaveValue#profile()} set true
	 * @return builder instance
	 */
	public CleanerBuilder cleanProfile(boolean cleanProfile)
	{
		this.cleanProfile = cleanProfile;
		return this;
	}

	/**
	 * @param cleanNormal true if #clean# instance should clean values that are not mark as {@link SaveValue#profile()} or {@link
	 *                    SaveValue#persistent()}
	 * @return builder instance
	 */
	public CleanerBuilder cleanNormal(boolean cleanNormal)
	{
		this.cleanNormal = cleanNormal;
		return this;
	}

	/**
	 * @param cleanClass preferences class on which values cleaning should take place, if class is null or is not set values of all
	 *                   preferences will be cleaned according to other properties
	 * @return builder instance
	 */
	public CleanerBuilder cleanClass(Class cleanClass)
	{
		this.cleanClass.add(cleanClass);
		return this;
	}

	/**
	 * method set builder parameters that #cleaner# will remove all saved entries
	 *
	 * @return builder instance
	 */
	public CleanerBuilder cleanAll()
	{
		return cleanClass(null).cleanNormal(true).cleanPersistent(true).cleanProfile(true);
	}

	public boolean isCleanPersistent()
	{
		return cleanPersistent;
	}

	public boolean isCleanProfile()
	{
		return cleanProfile;
	}

	public boolean isCleanNormal()
	{
		return cleanNormal;
	}

	public Set<Class> getCleanClass()
	{
		return cleanClass;
	}

	public Cleaner build()
	{
		Set<Cleaner> cleaners = new HashSet<>();
		if (isCleanPersistent())
		{
			cleaners.add(new PersistentCleaner());
		}
		if (isCleanNormal())
		{
			cleaners.add(new NonPersistentCleaner());
		}
		if (isCleanPersistent())
		{
			throw new IllegalStateException("cleaning profile not supported yet");
		}
		if (!getCleanClass().isEmpty())
		{
			throw new IllegalStateException("cleaning profile not supported yet");
		}
		MultiCleaner multiCleaner = new MultiCleaner(cleaners);
		return multiCleaner;
	}
}
