package pl.kalisz.kamil.preffer.serializer;

public class CustomObject
{
	private String myString;

	public CustomObject(Double doubleValue)
	{
		this.myString =""+doubleValue;
	}

	public String getMyString()
	{
		return myString;
	}


	@Override public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		CustomObject that = (CustomObject) o;

		return !(myString != null ? !myString.equals(that.myString) : that.myString != null);

	}

	@Override public int hashCode()
	{
		return myString != null ? myString.hashCode() : 0;
	}

	@Override public String toString()
	{
		return "CustomObject{" +
				"myString='" + myString + '\'' +
				'}';
	}
}
