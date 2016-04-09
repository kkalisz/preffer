package pl.kalisz.kamil.preffer.clean;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.HashSet;

import pl.kalisz.kamil.preffer.MemoryStore;
import pl.kalisz.kamil.preffer.Preffer;

/**
 * Copyright (C) 2016 Kamil Kalisz.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class MultiCleanerTest extends TestCase
{

    public void testWhenAddNoCleanerNothingIsCleaned()
    {
        MultiCleaner multiCleaner = new MultiCleaner(new HashSet<Cleaner>());

        multiCleaner.clean(new MemoryStore());
    }

    public void testWhenAddNonPersistentCleanerOnlyNonPersistentValuesAreCleared()
    {
        MemoryStore memoryStore = new MemoryStore();
        Preffer preffer = new Preffer(memoryStore);
        TestPreferences testPreferences = preffer.get(TestPreferences.class);
        testPreferences.saveNonPersistentValue("NO_PERSISTENT_VALUE");
        testPreferences.savePersistentValue("PERSISTENT_VALUE");

        MultiCleaner multiCleaner = new MultiCleaner(new HashSet<Cleaner>());
        multiCleaner.addCleaner(new NonPersistentCleaner());

        preffer.clean(multiCleaner);

        Assert.assertNull(testPreferences.getNonPersistentValue());
        Assert.assertEquals("PERSISTENT_VALUE",testPreferences.getPersistentValue());
    }

    public void testWhenAddNonPersistentCleanerAndPersistentCleanerValuesAreCleared()
    {
        MemoryStore memoryStore = new MemoryStore();
        Preffer preffer = new Preffer(memoryStore);
        TestPreferences testPreferences = preffer.get(TestPreferences.class);
        testPreferences.saveNonPersistentValue("NO_PERSISTENT_VALUE");
        testPreferences.savePersistentValue("PERSISTENT_VALUE");

        MultiCleaner multiCleaner = new MultiCleaner(new HashSet<Cleaner>());
        multiCleaner.addCleaner(new NonPersistentCleaner());
        multiCleaner.addCleaner(new PersistentCleaner());

        preffer.clean(multiCleaner);

        Assert.assertNull(testPreferences.getNonPersistentValue());
        Assert.assertNull( testPreferences.getPersistentValue());
    }

}