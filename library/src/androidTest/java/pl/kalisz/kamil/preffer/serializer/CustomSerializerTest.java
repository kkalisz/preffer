package pl.kalisz.kamil.preffer.serializer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;
import pl.kalisz.kamil.preffer.Preffer;
import pl.kalisz.kamil.preffer.store.SharedPreferencesStore;

public class CustomSerializerTest extends ActivityInstrumentationTestCase2
{

	private static final String TEST_PREFERENCES = "TEST_PREFERENCES";

	public CustomSerializerTest() {
		super(Activity.class);
		setName("name");
	}

	private Preffer preffer;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(TEST_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferencesStore sharedPreferencesStore= new SharedPreferencesStore(sharedPreferences);
		preffer= new Preffer(sharedPreferencesStore);

	}
	public void testCustomSerializeObject()
	{
		CustomObjectPreferences customObjectPreferences = preffer.get(CustomObjectPreferences.class);

		CustomObject customObject = new CustomObject(123d);
		customObjectPreferences.save(customObject);

		CustomObject loadesClass = customObjectPreferences.load();


		Assert.assertEquals(loadesClass, customObject);
	}
}
