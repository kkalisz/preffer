package pl.kalisz.kamil.preffer.serializer;

public class CustomObjectSerializer implements Serializer<CustomObject>
{


	@Override public String serialize(Class<CustomObject> type, CustomObject objectToSerialize)
	{
		if(objectToSerialize == null)
		{
			return null;
		}
		else return objectToSerialize.getMyString();
	}

	@Override public CustomObject deserialize(Class<CustomObject> type, String objectToDeSerialize)
	{
		if(objectToDeSerialize == null)
		{
			return null;
		}
		return new CustomObject(Double.parseDouble(objectToDeSerialize));
	}
}
