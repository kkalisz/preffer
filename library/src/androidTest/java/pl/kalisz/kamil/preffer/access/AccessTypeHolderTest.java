package pl.kalisz.kamil.preffer.access;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by kkalisz on 10.05.15.
 */
public class AccessTypeHolderTest extends TestCase {
    public void testVoidMethodWithNoParamsThrowException() {
        Method method = extractMethod(TestClassWithMethods.class, "voidMethodWithNoParam");

        try {
            AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testVoidMethodWithOneParamIsSetAccessTypeWithStringParam()
    {
        Method method = extractMethod(TestClassWithMethods.class, "voidMethodWithOneParam");

        AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);

        Assert.assertEquals(accessTypeHolder.getAccessType(),AccessType.SET);
        Assert.assertEquals(accessTypeHolder.getAccessValueClass(),String.class);
    }

    public void testVoidMethodWithTwoParamsThrowException() {
        Method method = extractMethod(TestClassWithMethods.class, "voidMethodWithTwoParams");

        try {
            AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testStringMethodWithNoParamIsGetAccessTypeWithBigDecimalParam()
    {
        Method method = extractMethod(TestClassWithMethods.class, "stringMethodWithNoParam");

        AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);

        Assert.assertEquals(accessTypeHolder.getAccessType(),AccessType.GET);
        Assert.assertEquals(accessTypeHolder.getAccessValueClass(),BigDecimal.class);
    }

    public void testStringMethodWithOneParamThrowException() {
        Method method = extractMethod(TestClassWithMethods.class, "stringMethodWithOneParam");

        try {
            AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testStringMethodWithTwoParamsThrowException() {
        Method method = extractMethod(TestClassWithMethods.class, "stringMethodWithTwoParams");

        try {
            AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }




    private Method extractMethod(Class classToExtractMethods, String methodName) {
        Method[] declaringMethods = classToExtractMethods.getDeclaredMethods();
        for (int i = 0; i < declaringMethods.length; i++) {
            Method method = declaringMethods[i];
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

}