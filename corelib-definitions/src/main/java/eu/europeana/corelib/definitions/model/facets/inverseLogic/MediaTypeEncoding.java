package eu.europeana.corelib.definitions.model.facets.inverseLogic;

public enum MediaTypeEncoding {
    IMAGE(1),
    SOUND(2),
    VIDEO(3),
    TEXT(4),
    INVALID_VALUE(0);

    private final int value;

    MediaTypeEncoding(final int value) {
        this.value = value;
    }

    public int getValue()          {return value;}
    public int getEncodedValue()   {return value << TagEncoding.MEDIA_TYPE.getBitPos();}

    public static MediaTypeEncoding valueOf(final Integer tag) {
        if (1 == tag) {
            return IMAGE;
        }
        else if (2 == tag) {
            return SOUND;
        }
        else if (3 == tag) {
            return VIDEO;
        }
        else if (4 == tag) {
            return TEXT;
        }

        return INVALID_VALUE;
    }
}
