package pl.kalisz.kamil.preffer.clean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.text.TextUtils;

import junit.framework.Assert;

import pl.kalisz.kamil.preffer.Preffer;
import pl.kalisz.kamil.preffer.store.SharedPreferencesStore;

public class PersistentCleanerTest extends ActivityUnitTestCase<Activity>
{

    private static final String TEST_PREFERENCES = "TEST_PREF";
    private Preffer preffer;
    private SharedPreferencesStore store;

    public PersistentCleanerTest() {
        super(Activity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        startActivity(new Intent(getInstrumentation().getTargetContext(),Activity.class),new Bundle(),null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(TEST_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        store = new SharedPreferencesStore(sharedPreferences);
        this.preffer = new Preffer(store);
    }

    public void testNonPersistentCleanerWillRemoveOnlyNonPersistentValues()
    {
        TestPreferences testPreferences = preffer.preffer(TestPreferences.class);
        testPreferences.saveNonPersistentValue("someValue");
        testPreferences.savePersistentValue("someOtherValue");

        Cleaner persistentCleaner = new PersistentCleaner();
        persistentCleaner.clean(store);

        String persistentValue = testPreferences.getPersistentValue();
        String nonPersistentValue = testPreferences.getNonPersistentValue();

        Assert.assertTrue(TextUtils.isEmpty(persistentValue));
        Assert.assertFalse(TextUtils.isEmpty(nonPersistentValue));
    }

}