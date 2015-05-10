package pl.kalisz.kamil.preffer;

import android.text.TextUtils;

import java.lang.reflect.Method;

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


    public SaveValueHelper(Method method) throws IllegalArgumentException
    {
        if(!method.isAnnotationPresent(SaveValue.class))
        {
            throw new IllegalArgumentException("Method must be annotated with SaveValue");
        }
        saveValueAnnotation = method.getAnnotation(SaveValue.class);
        if(TextUtils.isEmpty(saveValueAnnotation.key()))
        {
            throw new IllegalArgumentException("SavedValue.key can't be empty");
        }
        accessTypeHolder = new AccessTypeHolder(method);
    }


    public SaveValue getAnnotation() {
        return saveValueAnnotation;
    }


    public AccessTypeHolder getAccessTypeHolder()
    {
        return accessTypeHolder;
    }




}
