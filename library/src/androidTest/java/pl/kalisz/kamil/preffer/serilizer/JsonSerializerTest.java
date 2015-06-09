package pl.kalisz.kamil.preffer.serilizer;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.math.BigDecimal;

/**
 * Created by kamil.kalisz on 2015-06-09.
 */
public class JsonSerializerTest extends TestCase
{
    public void testSerializeNullValueReturnNullString()
    {
        JsonSerializer jsonSerializer = new JsonSerializer();

        String serializedValue = jsonSerializer.serialize(Double.class,null);

        Assert.assertNull(serializedValue);
    }

    public void testDeserializeNullValueReturnNullString()
    {
        JsonSerializer jsonSerializer = new JsonSerializer();

        Double deserializedValue = jsonSerializer.deserialize(Double.class, null);

        Assert.assertNull(deserializedValue);
    }

    public void testSerilizeAndDeserilizeBigDecimalValueReturnCorrectValue()
    {
        JsonSerializer jsonSerializer = new JsonSerializer();
        BigDecimal value = new BigDecimal(5756.65);

        String serializedValue = jsonSerializer.serialize(BigDecimal.class,value);
        BigDecimal deserializedValue = jsonSerializer.deserialize(BigDecimal.class, serializedValue);

        Assert.assertEquals(value,deserializedValue);
    }
}