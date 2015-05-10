package pl.kalisz.kamil.preffer.access;

import java.lang.reflect.Method;

/**
 * Helper class that extract information about return type and parameter of method,
 * validate them an return information about method {@link AccessType}
 */
public class AccessTypeHolder
{
    private final AccessType accessType;
    private final Class accessValueClass;

    public AccessTypeHolder(Method method)
    {
        Class returnType =  method.getReturnType();
        Class[] parameterTypes = method.getParameterTypes();

        if(parameterTypes.length > 1)
        {

        }

        boolean isReturn = returnType != Void.class;
        boolean isParameter = parameterTypes.length == 1;

        if(isReturn == isParameter)
        {

        }

        if(isParameter)
        {
            accessType = AccessType.SET;
            accessValueClass = parameterTypes[0];
        }
        else
        {
            accessType = AccessType.GET;
            accessValueClass = returnType;
        }
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public Class getAccessValueClass() {
        return accessValueClass;
    }
}
