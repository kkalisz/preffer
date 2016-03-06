package pl.kalisz.kamil.preffer.store;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Store implementation that uses android {@link SharedPreferences}
 */
public class SharedPreferencesStore implements Store {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesStore(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public synchronized void setValue(String key, String value)
    {
        sharedPreferences.edit().putString(key,value).commit();
    }

    @Override
    public synchronized String getValue(String key) {
        return sharedPreferences.getString(key,null);
    }

    @Override
    public Set<String> getKeys() {
        return new HashSet<>(sharedPreferences.getAll().keySet());
    }
}
