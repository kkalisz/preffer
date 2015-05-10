package pl.kalisz.kamil.preffer;

import pl.kalisz.kamil.preffer.annotations.SaveValue;

/**
 * Created by kkalisz on 10.05.15.
 */
public interface TestAnnotatedClass
{
    String methodWithoutSaveValueAnnotation();

    @SaveValue(key = "")
    String methodWithSaveValueAnnotationAndEmptyKey();

    @SaveValue(key = "SOME_KEY")
    String methodWithSaveValueAnnotationAndNotEmptyKey();



}
