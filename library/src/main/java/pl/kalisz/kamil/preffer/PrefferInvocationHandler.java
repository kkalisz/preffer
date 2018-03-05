package pl.kalisz.kamil.preffer;

import android.text.TextUtils;
import android.util.LruCache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.serializer.Serializer;
import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.ProfileStore;
import pl.kalisz.kamil.preffer.store.Store;

/**
 * Invoation handler that will use invoked method types/parameters to invoke save and load operation on Store
 */
public class PrefferInvocationHandler implements InvocationHandler
{
	private final Store delegate;

	private final String profile;

	private Serializer serializer;

	private LruCache<Method,Accessor> accessorLruCache = new LruCache<Method,Accessor>(12)
	{
		@Override
		protected Accessor create(Method method) {
			return createAccessor(method);
		}
	};


	//visible for Testing
	Accessor createAccessor(Method method) {
		if(!method.isAnnotationPresent(SaveValue.class))
        {
            throw new IllegalArgumentException("Method must be annotated with SaveValue");
        }
		SaveValue saveValueAnnotation = method.getAnnotation(SaveValue.class);
		if(TextUtils.isEmpty(saveValueAnnotation.key()))
        {
            throw new IllegalArgumentException("SavedValue.key can't be empty");
        }
		Store store = resolveSaveStore(saveValueAnnotation);
		Serializer serializer = resolveSerializer(saveValueAnnotation);
		return new Accessor(new AccessTypeHolder(method),store,serializer,saveValueAnnotation);
	}

	public PrefferInvocationHandler(Store delegate, String profile, Serializer serializer)
	{
		this.profile = profile;
		this.delegate = delegate;
		this.serializer = serializer;
	}

	private Store resolveSaveStore(SaveValue saveValueAnnotation)
	{
		Store store = delegate;
		if (saveValueAnnotation.profile())
		{
			store = new ProfileStore(delegate, profile);
		}
		if (saveValueAnnotation.persistent())
		{
			store = new PersistentStore(delegate);
		}
		return store;
	}

	@SuppressWarnings("unchecked") @Override public Object invoke(Object proxy, Method method, Object[] args)
	{
		return accessorLruCache.get(method).access(args);
	}

	/**
	 * @param annotation annotation that contains information about custom serializer
	 * @return default serializer or custom serializer if definition is present in annotation
	 */
	private Serializer resolveSerializer(SaveValue annotation)
	{
		Class<? extends Serializer> serializerClass = annotation.serializer();
		if (serializerClass != Serializer.class)
		{
			return createNewSerializerInstance(serializerClass);
		}
		return serializer;
	}

	/**
	 * @param serializerClass customs serializer class
	 * @return new serializerClass instance
	 */
	private Serializer createNewSerializerInstance(Class<? extends Serializer> serializerClass)
	{
		try
		{
			return serializerClass.newInstance();
		} catch (Exception e)
		{
			throw new IllegalStateException(String.format("Can't create Serializer of cass %s", serializerClass.getCanonicalName()), e);
		}
	}



}
