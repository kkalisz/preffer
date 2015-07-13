package pl.kalisz.kamil.preffer.serializer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

public interface CustomObjectPreferences
{
	String TAG ="TAG";

	@SaveValue(key = TAG,serializer = CustomObjectSerializer.class)
	void save(CustomObject customObject);

	@SaveValue(key = TAG, serializer = CustomObjectSerializer.class) CustomObject load();
}
