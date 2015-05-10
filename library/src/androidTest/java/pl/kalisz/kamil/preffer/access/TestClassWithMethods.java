package pl.kalisz.kamil.preffer.access;

import java.math.BigDecimal;

/**
 * Created by kkalisz on 10.05.15.
 */
public interface TestClassWithMethods
{
    void voidMethodWithNoParam(); // bad

    void voidMethodWithOneParam(String param); //good

    void voidMethodWithTwoParams(String param1,String param2); // bad

    BigDecimal stringMethodWithNoParam(); // good

    String stringMethodWithOneParam(String param1); // bad (when we add api to provide get method with default values this will be "good" )

    BigDecimal stringMethodWithTwoParams(String param1,String param2); // bad



}
