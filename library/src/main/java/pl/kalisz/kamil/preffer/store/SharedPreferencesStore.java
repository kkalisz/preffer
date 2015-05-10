package pl.kalisz.kamil.preffer.store;

import android.content.SharedPreferences;

/**
 * Created by kkalisz on 10.05.15.
 */
public class SharedPreferencesStore implements Store {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesStore(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setValue(String key, String value)
    {
        sharedPreferences.edit().putString(key,value).commit();
    }

    @Override
    public String getValue(String key) {
        return sharedPreferences.getString(key,null);
    }
}
