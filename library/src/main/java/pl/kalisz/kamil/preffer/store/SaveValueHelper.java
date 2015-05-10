package pl.kalisz.kamil.preffer.store;

import android.text.TextUtils;

import java.lang.reflect.Method;

import pl.kalisz.kamil.preffer.access.AccessType;
import pl.kalisz.kamil.preffer.access.AccessTypeHolder;
import pl.kalisz.kamil.preffer.annotations.SaveValue;

/**
 * Helper class that extract information from method, it also take care of validating input/output
 * parameters
 */
public class SaveValueHelper
{
    private SaveValue saveValueAnnotation;

    private AccessTypeHolder accessTypeHolder;

    public static SaveValueHelper buildIfCorrectMethod(Method method) {
        if (method.isAnnotationPresent(SaveValue.class)) {
            return new SaveValueHelper(method);
        }
        return null;
    }

    private SaveValueHelper(Method method) {
        saveValueAnnotation = method.getAnnotation(SaveValue.class);

        accessTypeHolder = new AccessTypeHolder(method);


    }


    public void throwIfEmptyKey()
    {
        if(TextUtils.isEmpty(saveValueAnnotation.key()))
        {
            throw new IllegalStateException("SavedValue.key can't be empty");
        }
    }


    public void throwIfIllegalParameters()
    {

    }


    public SaveValue getAnnotation() {
        return saveValueAnnotation;
    }

    public void validate() {
        throwIfEmptyKey();
        throwIfIllegalParameters();
    }

    public AccessTypeHolder getAccessTypeHolder()
    {
        return accessTypeHolder;
    }




}
