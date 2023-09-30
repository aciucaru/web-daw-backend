package daw.backend.controller.constants;

public enum HttpHeaderValue
{
    CONTENT_JSON("application/json; charset=UTF8"),
    CONTENT_PLAIN_TEXT("text/pain");

    public final String value;

    private HttpHeaderValue(String value)
    {
        this.value = value;
    }
}
