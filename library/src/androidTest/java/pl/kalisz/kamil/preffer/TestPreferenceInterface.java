package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

/**
 * Created by kkalisz on 10.05.15.
 */
public interface TestPreferenceInterface
{
    @SaveValue(key = "RUN_TEST")
    void setRunTest(String runTest);

    @SaveValue(key = "RUN_TEST")
    String getRunTest();

}
