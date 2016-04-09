package pl.kalisz.kamil.preffer.serializer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

public interface CustomObjectPreferences {
    String TAG = "TAG";
    String LAST_VIEWED_CATEGORY = "LAST_VIEWED_CATEGORY";

    @SaveValue(key = TAG, serializer = CustomObjectSerializer.class)
    void save(CustomObject customObject);

    @SaveValue(key = TAG, serializer = CustomObjectSerializer.class)
    CustomObject load();

    @SaveValue(key = LAST_VIEWED_CATEGORY, serializer = CustomIncorrectSerializer.class)
    void setlastViewedCategory(String lastViewedCategory);

    @SaveValue(key = LAST_VIEWED_CATEGORY, serializer = CustomIncorrectSerializer.class)
    String getLastViewedCategory(String defaultValue);
}
