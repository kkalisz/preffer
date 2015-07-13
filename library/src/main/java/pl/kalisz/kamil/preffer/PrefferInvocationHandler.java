package pl.kalisz.kamil.preffer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;
import pl.kalisz.kamil.preffer.serializer.JsonSerializer;
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

	private Serializer serializer = new JsonSerializer();

	public PrefferInvocationHandler(Store delegate, String profile, Serializer serializer)
	{
		this.profile = profile;
		this.delegate = delegate;
		this.serializer = serializer;
	}


	@SuppressWarnings("unchecked") @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		SaveValueHelper saveValueHelper = new SaveValueHelper(method);
		return handlePreferenceMethod(args, saveValueHelper);
	}

	private Object handlePreferenceMethod(Object[] args, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		switch (accessTypeHolder.getAccessType())
		{
			case GET:
				return invokeGetPreference(args, saveValueHelper);
			case SET:
				invokeSetMethod(args, saveValueHelper);
				break;
		}
		return null;
	}

	private void invokeSetMethod(Object[] methodArguments, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		SaveValue annotation = saveValueHelper.getAnnotation();

		Serializer serializer = resolveSerializer(annotation);
		String serializedValue = serializer.serialize(accessTypeHolder.getAccessValueClass(), methodArguments[0]);

		setValueToStore(annotation, serializedValue);
	}

	private Object invokeGetPreference(Object[] methodArguments, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		SaveValue annotation = saveValueHelper.getAnnotation();

		String valueToDeserialize = getValueFromStore(annotation);
		Serializer serializer = resolveSerializer(annotation);

		Object returnObject = serializer.deserialize(accessTypeHolder.getAccessValueClass(), valueToDeserialize);
		return returnValueOrDefaultIfPresent(returnObject, methodArguments, accessTypeHolder);
	}

	private Object returnValueOrDefaultIfPresent(Object deserializedObject, Object[] methodArguments, AccessTypeHolder accessTypeHolder)
	{
		if (deserializedObject == null && accessTypeHolder.hasDefaultValue())
		{
			deserializedObject = methodArguments[0];
		}
		return deserializedObject;
	}

	private String getValueFromStore(SaveValue annotation)
	{
		Store saveStore = resolveSaveStore(annotation);
		return saveStore.getValue(annotation.key());
	}


	private Serializer resolveSerializer(SaveValue annotation)
	{
		Class<? extends Serializer> serializerClass = annotation.serializer();
		if (serializerClass != Serializer.class)
		{
			return createNewSerializerInstance(serializerClass);
		}
		return serializer;
	}

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

	public void setValueToStore(SaveValue annotation, String valueToSave)
	{
		Store saveStore = resolveSaveStore(annotation);
		saveStore.setValue(annotation.key(), valueToSave);
	}

}
