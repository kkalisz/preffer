# Preffer


Preffer is simple library for implementing Preferences on Android platform

Creating preference is very simple
```java
Preffer preffer = .....
LanguagePreferences languagePreferences = preffer.get(LanguagePreferences.class);
String selectedLanguage = languagePreferences.getSelectedLanguage("PL");
```

LangaugePreferences looks this
```java
public interface LangaugePreferences
{
	String SELECTED_LANGUAGE_KEY = "LANGAUGE_PREFERENCES_SELECTED_LANGUAGE";

	@SaveValue(key = SELECTED_LANGUAGE_KEY)
	void setSelectedLanguage(String selectedLanguage);

	@SaveValue(key = SELECTED_LANGUAGE_KEY)
	String getSelectedLanguage(String defaultValue);
}
```

First off all is creating Preffer object which is responsible for generetaing runtime preferences implementation.
```java
Preffer preffer = new Preffer(store);
```

To create Preffer you nedd to have Store implementation, default provided implementation is SharedPreferencesStore

```java
SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppStore", Context.MODE_PRIVATE);
Store sharedPreferencesStore = new SharedPreferencesStore(sharedPreferences);
```

@SaveValue annotation has 3 paramaters
```java
    @SaveValue(key = "MY_PREFERENCE_KETY",persistent = true, profile = true)
```
* key is unique key for preference
* persistant - inndicates that normal clean preferences should or should not delete this preference
* profile - value indicates that this preference is profile dependent ( You can have diffrent preferences for diffrent users )


### Example Preferences:
```java
public interface TestPreferences {

    String MY_PROFILE_PREFERENCE_KEY = "TEST_PREFERENCES_MY_PROFILE_PREFERENCE";

    String MY_PERSISTENT_PREFERENCE_KEY = "TEST_PREFERENCES_MY_PERSISTENT_PREFERENCE";

    String MY_PREFERENCE_WITH_DEFAULT_VALUE_KEY = "TEST_PREFERENCES_MY_PREFERENCE_WITH_DEFAULT_VALUE";

    @SaveValue(key = MY_PROFILE_PREFERENCE_KEY, profile = true)
    void setMyProfilePreference(Date myProfilePreference);  // profile dependent value

    @SaveValue(key = MY_PROFILE_PREFERENCE_KEY, profile = true)
    Date getMyProfilePreference();

    @SaveValue(key = MY_PERSISTENT_PREFERENCE_KEY, persistent = true) // persistent value
    void setMyPersistentPreference(String myPersistentPreference);

    @SaveValue(key = MY_PERSISTENT_PREFERENCE_KEY, persistent = true)
    String getMyPersistentPreference();

    @SaveValue(key = MY_PREFERENCE_WITH_DEFAULT_VALUE_KEY)
    void setMyPreferenceWithDefaultValue(Currency myPreferenceWithDefaultValue);

    @SaveValue(key = MY_PREFERENCE_WITH_DEFAULT_VALUE_KEY)
    Currency getMyPreferenceWithDefaultValue(Currency myPreferenceWithDefaultValue);  // preference getter with default value parameter
}
```

### CustomStore:

You can implmenet custom store to save preferences, other posibility is to create Store that encrypt values and keys and delegate set/get methods to other Store
```java
public class AesStore implements Store
{

	private AesCipher encryptCipher;

	private AesCipher decryptCipher;

	private Store delegate;

	@Override
	public void setValue(String key, String value)
	{
		String encryptedKey = encryptCipher.doFinal(key);
		String encryptedValue = encryptCipher.doFinal(value);
		delegate.setValue(encryptedKey, encryptedValue);
	}

	@Override
	public String getValue(String key)
	{
		String encryptedKey = encryptCipher.doFinal(key);
		String encryptedValue = delegate.getValue(encryptedKey);
		return (decryptCipher.doFinal(encryptedValue));
	}

	@Override
	public Set<String> getKeys()
	{
		Set<String> decryptedKeys = new HashSet<String>();
		for(String encryptedKey :delegate.getKeys())
		{
			decryptedKeys.add(decryptCipher.doFinal(encryptedKey));
		}
		return decryptedKeys;
	}
}
```
