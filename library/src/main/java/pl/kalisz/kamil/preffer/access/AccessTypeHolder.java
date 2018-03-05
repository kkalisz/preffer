package pl.kalisz.kamil.preffer.access;

import java.lang.reflect.Method;

/**
 * Helper class that extract information about return type and parameter of method,
 * validate them an return information about method {@link AccessType}
 */
public class AccessTypeHolder
{
    /**
     * enum that represents type of operation on preferences
     */
    public enum AccessType
    {
        SET,
        GET
    }

    private final AccessType accessType;
    private final Class accessValueClass;
    private boolean hasDefaultValue;

    public AccessTypeHolder(Method method)
    {
        Class returnType =  method.getReturnType();
        Class[] parameterTypes = method.getParameterTypes();

        if(parameterTypes.length > 1)
        {
            throw new IllegalArgumentException("Save method must have exactly one parameter, load method can't have one parameters with default value");
        }

        boolean isReturn = returnType != void.class;
        boolean isParameter = parameterTypes.length == 1;

        if(isReturn && isParameter && returnType != parameterTypes[0])
        {
            throw new IllegalArgumentException("Access Save method should have void return type and one parameter, Load method should have non void return type on one optional parameters same type as return type ");
        }
        if(!isParameter && !isReturn)
        {
            throw new IllegalArgumentException("Access Save method should have void return type and one parameter, Load method should have non void return type on one optional parameters same type as return type ");
        }

        if(isReturn)
        {
            accessType = AccessType.GET;
            accessValueClass = returnType;
            hasDefaultValue = isParameter;
        }
        else
        {
            accessType = AccessType.SET;
            accessValueClass = parameterTypes[0];
        }
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public Class getAccessValueClass() {
        return accessValueClass;
    }

    public boolean hasDefaultValue() {
        return hasDefaultValue;
    }
}
