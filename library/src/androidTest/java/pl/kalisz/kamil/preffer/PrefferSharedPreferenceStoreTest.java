package pl.kalisz.kamil.preffer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import pl.kalisz.kamil.preffer.store.SharedPreferencesStore;

/**
 * Created by kkalisz on 10.05.15.
 */
public class PrefferSharedPreferenceStoreTest extends ActivityInstrumentationTestCase2
{
    private static final String TEST_PREFERENCES = "TEST_PREFERENCES";

    public PrefferSharedPreferenceStoreTest() {
        super(Activity.class);
    }

    private Preffer preffer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(TEST_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferencesStore sharedPreferencesStore= new SharedPreferencesStore(sharedPreferences);
        preffer= new Preffer(sharedPreferencesStore);

    }

    public void tesSetAndGetValueReturnPoperValue()
    {
        TestPreferenceInterface testPreferenceInterface = preffer.preffer(TestPreferenceInterface.class);
        String value = "MyValue";

        testPreferenceInterface.setRunTest(value);
        String loadedValue =  testPreferenceInterface.getRunTest();

        Assert.assertEquals(value,loadedValue);
    }

}
