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

	/**
	 /**
	 * @param methodArguments arguments of call preference method
	 * @param saveValueHelper definition of preference method
	 * @return method result or null if method return type is void
	 */
	private Object handlePreferenceMethod(Object[] methodArguments, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		switch (accessTypeHolder.getAccessType())
		{
			case GET:
				return invokeGetPreference(methodArguments, saveValueHelper);
			case SET:
				invokeSetMethod(methodArguments, saveValueHelper);
				break;
		}
		return null;
	}

	/**
	 * @param methodArguments arguments of call preference method
	 * @param saveValueHelper definition of preference method
	 */
	private void invokeSetMethod(Object[] methodArguments, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		SaveValue annotation = saveValueHelper.getAnnotation();

		Serializer serializer = resolveSerializer(annotation);
		String serializedValue = serializer.serialize(accessTypeHolder.getAccessValueClass(), methodArguments[0]);

		setValueToStore(annotation, serializedValue);
	}

	/**
	 * @param methodArguments arguments of call preference method
	 * @param saveValueHelper definition of preference method
	 * @return preference value
	 */
	private Object invokeGetPreference(Object[] methodArguments, SaveValueHelper saveValueHelper)
	{
		AccessTypeHolder accessTypeHolder = saveValueHelper.getAccessTypeHolder();
		SaveValue annotation = saveValueHelper.getAnnotation();

		String valueToDeserialize = getValueFromStore(annotation);
		Serializer serializer = resolveSerializer(annotation);

		Object returnObject = serializer.deserialize(accessTypeHolder.getAccessValueClass(), valueToDeserialize);
		return returnValueOrDefaultIfPresent(returnObject, methodArguments, accessTypeHolder);
	}

	/**
	 * @param deserializedObject deserialized preference value
	 * @param methodArguments arguments of method used to get default preference value
	 * @param accessTypeHolder definition of preference method
	 * @return return if deserializedObject value is null and defaultValue is not null, return defaultValue else return deserializedObject
	 */
	private Object returnValueOrDefaultIfPresent(Object deserializedObject, Object[] methodArguments, AccessTypeHolder accessTypeHolder)
	{
		if (deserializedObject == null && accessTypeHolder.hasDefaultValue())
		{
			deserializedObject = methodArguments[0];
		}
		return deserializedObject;
	}

	/**
	 * @param annotation annotation that contains definition of preference
	 * @return value of preference form store
	 */
	private String getValueFromStore(SaveValue annotation)
	{
		Store saveStore = resolveSaveStore(annotation);
		return saveStore.getValue(annotation.key());
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

	/**
	 * @param saveValueAnnotation annotation that contains store definition
	 * @return store builded using data from saveValueAnnotation
	 */
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

	/**
	 * @param annotation annotation with preference definition
	 * @param valueToSave value to save
	 */
	public void setValueToStore(SaveValue annotation, String valueToSave)
	{
		Store saveStore = resolveSaveStore(annotation);
		saveStore.setValue(annotation.key(), valueToSave);
	}

}
