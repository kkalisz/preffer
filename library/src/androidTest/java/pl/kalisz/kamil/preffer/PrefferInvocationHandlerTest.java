package pl.kalisz.kamil.preffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.Set;

import pl.kalisz.kamil.preffer.access.TestClassWithMethods;
import pl.kalisz.kamil.preffer.serializer.JsonSerializer;
import pl.kalisz.kamil.preffer.store.Store;

public class PrefferInvocationHandlerTest extends TestCase {

    private Store emptyStore = new Store() {
        @Override
        public void setValue(String key, String value) {

        }

        @Override
        public String getValue(String key) {
            return null;
        }

        @Override
        public Set<String> getKeys() {
            return null;
        }
    };

    private PrefferInvocationHandler prefferInvocationHandler = new PrefferInvocationHandler(emptyStore,null,new JsonSerializer());


    public void testStringMethodWithTwoParamsThrowException() {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "stringMethodWithTwoParams");

        try {
            prefferInvocationHandler.createAccessor(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testMethodWithoutSaveValueAnnotationThrowException() {
        Method method = MethodUtils.extractMethod(TestAnnotatedClass.class, "methodWithoutSaveValueAnnotation");

        try {
            prefferInvocationHandler.createAccessor(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testMethodWithSaveValueAnnotationAndEmptyKeyThrowException() {
        Method method = MethodUtils.extractMethod(TestAnnotatedClass.class, "methodWithSaveValueAnnotationAndEmptyKey");

        try {
            prefferInvocationHandler.createAccessor(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }


}