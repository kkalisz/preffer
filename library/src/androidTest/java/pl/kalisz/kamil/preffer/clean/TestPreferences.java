package pl.kalisz.kamil.preffer.clean;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

public interface TestPreferences
{

    String NON_PERSISTENT = "NON_PERSISTENT";

    String PERSISTENT = "PERSISTENT";

    @SaveValue(key = NON_PERSISTENT)
    String getNonPersistentValue();

    @SaveValue(key = NON_PERSISTENT)
    void saveNonPersistentValue(String value);

    @SaveValue(key = PERSISTENT)
    String getPersistentValue();

    @SaveValue(key = PERSISTENT)
    void savePersistentValue(String value);
}
