package pl.kalisz.kamil.preffer.clean;


import java.util.List;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

public interface OtherTestPreferences {

    @SaveValue(key = "sdfds")
    void save(List<TestData> value);

    @SaveValue(key = "sdfds")
    List<TestData> getValue();
}
