package pl.kalisz.kamil.preffer.access;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import pl.kalisz.kamil.preffer.MethodUtils;
import pl.kalisz.kamil.preffer.TestAnnotatedClass;

/**
 * Created by kkalisz on 10.05.15.
 */
public class AccessTypeHolderTest extends TestCase {
    public void testVoidMethodWithNoParamsThrowException() {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "voidMethodWithNoParam");

        try {
            new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testVoidMethodWithOneParamIsSetAccessTypeWithStringParam()
    {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "voidMethodWithOneParam");

        AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);

        Assert.assertEquals(accessTypeHolder.getAccessType(),AccessTypeHolder.AccessType.SET);
        Assert.assertEquals(accessTypeHolder.getAccessValueClass(),String.class);
    }

    public void testVoidMethodWithTwoParamsThrowException() {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "voidMethodWithTwoParams");

        try {
            new AccessTypeHolder(method);
            Assert.fail();

        } catch (IllegalArgumentException e) {
            //we expected exception
        }
    }

    public void testStringMethodWithNoParamIsGetAccessTypeWithBigDecimalParam()
    {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "stringMethodWithNoParam");

        AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);

        Assert.assertEquals(accessTypeHolder.getAccessType(),AccessTypeHolder.AccessType.GET);
        Assert.assertEquals(accessTypeHolder.getAccessValueClass(),BigDecimal.class);
    }

    public void testStringMethodWithOneParam() {
        Method method = MethodUtils.extractMethod(TestClassWithMethods.class, "stringMethodWithOneParam");
        AccessTypeHolder accessTypeHolder = new AccessTypeHolder(method);

        Assert.assertTrue(accessTypeHolder.hasDefaultValue());

    }
}