package pl.kalisz.kamil.preffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.access.TestClassWithMethods;

/**
 * Created by kkalisz on 10.05.15.
 */
public class SaveValueHelperTest extends TestCase {
    public void testMethodWithoutSaveValueAnnotationThrowException() {
        Method method = MethodUtils.extractMethod(TestAnnotatedClass.class, "methodWithoutSaveValueAnnotation");

        try {
            SaveValueHelper saveValueHelper = new SaveValueHelper(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testMethodWithSaveValueAnnotationAndEmptyKeyThrowException() {
        Method method = MethodUtils.extractMethod(TestAnnotatedClass.class, "methodWithSaveValueAnnotationAndEmptyKey");

        try {
            SaveValueHelper saveValueHelper = new SaveValueHelper(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testMethodWithSaveValueAnnotationAndNotEmptyKey() {
        Method method = MethodUtils.extractMethod(TestAnnotatedClass.class, "methodWithSaveValueAnnotationAndNotEmptyKey");

        SaveValueHelper saveValueHelper = new SaveValueHelper(method);
    }
}