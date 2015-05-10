package pl.kalisz.kamil.preffer;

import java.lang.reflect.Method;

/**
 * Created by kkalisz on 10.05.15.
 */
public class MethodUtils {
    public static Method extractMethod(Class classToExtractMethods, String methodName) {
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
