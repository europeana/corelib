package eu.europeana.corelib.search.service.inverseLogic;

public enum TagEncoding {
    TAG_TYPE(28, 3),
    MEDIA_TYPE(25, 3),
    MIME_TYPE(15, 10),

    IMAGE_SIZE(12, 3),
    IMAGE_COLOURSPACE(10, 2),
    IMAGE_ASPECTRATIO(8, 2),
    IMAGE_COLOUR(0, 8),

    SOUND_QUALITY(13, 2),
    SOUND_DURATION(10, 3),

    VIDEO_QUALITY(13, 2),
    VIDEO_DURATION(10, 3);


    private final int bitPos;
    private final int numOfBits;

    private TagEncoding(final int bitPos, final int numOfBits) {
        this.bitPos = bitPos;
        this.numOfBits = numOfBits;
    }

    public int getBitPos()           {return bitPos;}
    public int getNumOfBits()        {return numOfBits;}
    public int getMask()             {return ((1 << numOfBits) - 1) << bitPos;}
    public int extractValue(int tag) {return (tag & getMask()) >> bitPos;}
}
