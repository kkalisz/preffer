package pl.kalisz.kamil.preffer.store;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Set;

import pl.kalisz.kamil.preffer.MemoryStore;


/**
 * Copyright (C) 2016 Kamil Kalisz.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ProfileStoreTest extends TestCase
{
    private static String PROFILE_1 = "PROFILE_1";

    private static String PROFILE_2 = "PROFILE_2";


    private Store delegateStore;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        delegateStore = new MemoryStore();
    }

    public void testWhenPutValueToStoreGetValueWillReturnSameValue()
    {
        ProfileStore profileStore = new ProfileStore(delegateStore,PROFILE_1);

        String key = "MY_KEY";
        String value = "MY_VALUE";

        profileStore.setValue(key,value);

        Assert.assertSame(value,profileStore.getValue(key));
    }

    public void testWhenSaveVAlueToStoreAndGetKeysKeyWillBeInCollection()
    {
        ProfileStore profileStore = new ProfileStore(delegateStore,PROFILE_1);

        String key = "MY_KEY";
        String value = "MY_VALUE";

        profileStore.setValue(key,value);

        Set<String> storeKeys = profileStore.getKeys();
        Assert.assertTrue(storeKeys.contains(key));
        Assert.assertEquals(1,storeKeys.size());
    }

    public void testWhenSaveValueWithKeyTowoDiffrentProfileStoresEachValueIsIndependentSaved()
    {
        ProfileStore firstStore = new ProfileStore(delegateStore,PROFILE_1);
        ProfileStore secondStore = new ProfileStore(delegateStore,PROFILE_2);

        String key = "MY_KEY";
        String firstStoreValue = "FIRST_STORE_VALUE";
        String secondStoreValue = "FIRST_STORE_VALUE";

        firstStore.setValue(key,firstStoreValue);
        secondStore.setValue(key,secondStoreValue);

        Assert.assertSame(firstStoreValue,firstStore.getValue(key));
        Assert.assertSame(secondStoreValue,secondStore.getValue(key));

    }
}