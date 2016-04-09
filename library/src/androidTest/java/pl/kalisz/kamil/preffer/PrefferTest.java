package pl.kalisz.kamil.preffer;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PrefferTest extends TestCase
{

    public void testWhenTryCreatePreferencesThatAreNotInterfaceException()
    {
        Preffer preffer = new Preffer(new MemoryStore());

        try {
            String prefferences = preffer.get(String.class);
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            // we expect exception
        }
    }

    public void testWhenTryCreatePreferencesFormNullException()
    {
        Preffer preffer = new Preffer(new MemoryStore());

        Class<MyTestPreferences> preferenceClass = null;
        try {
            MyTestPreferences prefferences = preffer.get(preferenceClass);
            Assert.fail();
        }
        catch (NullPointerException e)
        {
            // we expect exception
        }
    }

    public void testWhenCreatePrefferWithProfileStoreProfilePreferencesAreProperSaved()
    {
        Preffer preffer = new Preffer(new MemoryStore(),"PROFILE");

        MyTestPreferences myTestPreferences = preffer.get(MyTestPreferences.class);
        myTestPreferences.setId("My_ID");

        Assert.assertEquals("My_ID", myTestPreferences.getId("Null"));
    }

    public void testWhenCreatePrefferWithProfileStoreProfileValueForOtherProfileIsNotSpecified()
    {
        MemoryStore store = new MemoryStore();
        Preffer profilePreffer = new Preffer(store,"PROFILE");
        Preffer secondProfilePreffer = new Preffer(store,"OTHER_PROFILE");

        MyTestPreferences myTestPreferences = profilePreffer.get(MyTestPreferences.class);
        myTestPreferences.setId("My_ID");

        MyTestPreferences secondPreferences = secondProfilePreffer.get(MyTestPreferences.class);

        Assert.assertEquals("Null",secondPreferences.getId("Null"));
    }
}
