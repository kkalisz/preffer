package pl.kalisz.kamil.preffer.store;

import org.json.JSONObject;

/**
 * Class that represent structure of context wrapped key
 */
public class ContextKey
{
    private static final String CONTEXT_KEY = "CONTEXT_KEY";

    private static final String RAW_KEY_KEY = "RAW_KEY_KEY";

    private final String context;

    private final String rawKey;

    public ContextKey(String context, String rawKey) {
        this.context = context;
        this.rawKey = rawKey;
    }

    public String generateContextKey()
    {
        try
        {
            JSONObject jsonContextKey = new JSONObject();
            jsonContextKey.put(CONTEXT_KEY, context);
            jsonContextKey.put(RAW_KEY_KEY, rawKey);
            return jsonContextKey.toString();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(String.format("can't create ContextKey for context: %s and key: %s",context,rawKey),e);
        }
    }

    public static ContextKey fromContextKeyString(String contextKey)
    {
        try {
            JSONObject jsonContextKey = new JSONObject(contextKey);
            String contextKeyValue = jsonContextKey.getString(CONTEXT_KEY);
            String rawKeyValue = jsonContextKey.getString(RAW_KEY_KEY);

            return new ContextKey(contextKeyValue, rawKeyValue);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(String.format("can't parse ContextKey from String %s",contextKey));
        }
    }

    public String getContext() {
        return context;
    }

    public String getRawKey() {
        return rawKey;
    }

    @Override
    public String toString() {
        return generateContextKey();
    }
}
