package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

public interface MyTestPreferences
{
    @SaveValue(key = "RUN_TEST")
    void setRunTest(String runTest);

    @SaveValue(key = "RUN_TEST")
    String getRunTest();

    @SaveValue(key = "PROFILE_ID",profile = true)
    void setId(String id);

    @SaveValue(key = "PROFILE_ID",profile = true)
    String getId(String defaultValue);

}
