package pl.kalisz.kamil.preffer;

import android.app.Activity;
import android.test.ActivityUnitTestCase;

import junit.framework.Assert;

import pl.kalisz.kamil.preffer.store.Store;

/**
 * Created by kkalisz on 10.05.15.
 */
public class PrefferTest extends ActivityUnitTestCase<Activity>
{
    public PrefferTest() {
        super(Activity.class);
    }

    private Preffer preffer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        preffer = new Preffer(new Store()
        {
            @Override
            public void setValue(String key, String value) {

            }

            @Override
            public String getValue(String key) {
                return null;
            }
        });
    }

    public void testWhenTryCreatePreferencesThatAreNotInterfaceException()
    {
        try {
            String prefferences = preffer.preffer(String.class);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            // we expect exception
        }
    }

    public void testWhenTryCreatePreferencesFormNullException()
    {
        Class<TestPreferenceInterface> preferenceClass = null;
        try {
            TestPreferenceInterface prefferences = preffer.preffer(preferenceClass);
            Assert.fail();
        }
        catch (NullPointerException e)
        {
            // we expect exception
        }
    }

}
