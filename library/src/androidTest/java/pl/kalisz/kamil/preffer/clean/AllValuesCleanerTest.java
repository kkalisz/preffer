package pl.kalisz.kamil.preffer.clean;

import junit.framework.Assert;
import junit.framework.TestCase;

import pl.kalisz.kamil.preffer.MemoryStore;
import pl.kalisz.kamil.preffer.store.PersistentStore;
import pl.kalisz.kamil.preffer.store.ProfileStore;
import pl.kalisz.kamil.preffer.store.Store;

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
public class AllValuesCleanerTest extends TestCase
{
    public static final String KEY_1 = "KEY1";
    public static final String KEY_2 = "KEY2";
    public static final String KEY_3 = "KEY3";
    private static String PROFILE_1 = "PROFILE_1";

    private Store delegateStore;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        delegateStore = new MemoryStore();
    }

    public void testWhenAddValuesToMixedStoresAfterCleanStoreIsEmpty()
    {
        ProfileStore profileStore = new ProfileStore(delegateStore,PROFILE_1);
        PersistentStore persistentProfileStore  = new PersistentStore(profileStore);
        PersistentStore persistentStore = new PersistentStore(delegateStore);

        profileStore.setValue(KEY_1,"value");
        persistentProfileStore.setValue(KEY_2,"value2");
        persistentStore.setValue(KEY_3,"value3");

        AllValuesCleaner allValuesCleaner = new AllValuesCleaner();
        Assert.assertEquals(3,delegateStore.getKeys().size());
        allValuesCleaner.clean(delegateStore);
        Assert.assertEquals(null,profileStore.getValue(KEY_1));
        Assert.assertEquals(null,persistentProfileStore.getValue(KEY_2));
        Assert.assertEquals(null,persistentProfileStore.getValue(KEY_3));
    }
}