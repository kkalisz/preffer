package pl.kalisz.kamil.preffer.store;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by kkalisz on 07.06.15.
 */
public class ContextKeyTest extends TestCase
{

    private static final String MY_RAW_KEY = "MY_RAW_KEY";
    private static final String MY_CONTEXT = "MY_CONTEXT";

    public void testSerializeAndDeserializeContextKeyReturnCorrectKey()
    {
        ContextKey contextKey = new ContextKey(MY_CONTEXT,MY_RAW_KEY);
        String contextKeyString = contextKey.generateContextKey();

        ContextKey deserializedContextKey = ContextKey.fromContextKeyString(contextKeyString);

        Assert.assertEquals(deserializedContextKey.getContext(),contextKey.getContext());
        Assert.assertEquals(deserializedContextKey.getRawKey(),contextKey.getRawKey());

    }
}