package pl.kalisz.kamil.preffer.clean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.text.TextUtils;
import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.kalisz.kamil.preffer.Preffer;
import pl.kalisz.kamil.preffer.store.SharedPreferencesStore;

public class NonPersistentCleanerTest extends ActivityUnitTestCase<Activity>
{

    private static final String TEST_PREFERENCES = "TEST_PREF";
    private Preffer preffer;
    private SharedPreferencesStore store;

    public NonPersistentCleanerTest() {
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
        TestPreferences testPreferences = preffer.get(TestPreferences.class);
        testPreferences.saveNonPersistentValue("someValue");
        testPreferences.savePersistentValue("someOtherValue");

        Cleaner nonPersistentCleaner = new NonPersistentCleaner();
        nonPersistentCleaner.clean(store);

        String persistentValue = testPreferences.getPersistentValue();
        String nonPersistentValue = testPreferences.getNonPersistentValue();

        Assert.assertTrue(TextUtils.isEmpty(nonPersistentValue));
        Assert.assertFalse(TextUtils.isEmpty(persistentValue));
    }

    public void test2fs() throws InterruptedException {
       OtherTestPreferences otherTestPreferences = preffer.get(OtherTestPreferences.class);
        List<TestData> testData = new ArrayList<>();
        for(int i =0 ; i< 6000; i++){
            testData.add(new TestData(new Date(),"3432",true));
        }
        Thread.sleep(1000);

        Log.d("start","save "+System.currentTimeMillis());
        otherTestPreferences.save(testData);
        Log.d("start","read "+System.currentTimeMillis());
        List<TestData> testData2 = otherTestPreferences.getValue();
        Log.d("start","end "+System.currentTimeMillis());
    }

}