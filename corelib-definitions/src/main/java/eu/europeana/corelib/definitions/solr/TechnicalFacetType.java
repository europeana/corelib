package eu.europeana.corelib.definitions.solr;

public enum TechnicalFacetType {

    MIME_TYPE("MIME_TYPE"),
    IMAGE_SIZE("IMAGE_SIZE"),
    IMAGE_ASPECTRATIO("IMAGE_ASPECTRATIO"),
    IMAGE_COLOUR("IMAGE_COLOUR"),
    COLOURPALETTE("COLOURPALETTE"),
    VIDEO_DURATION("VIDEO_DURATION"),
    VIDEO_HD("VIDEO_HD"),
    SOUND_HQ("SOUND_HQ"),
    SOUND_DURATION("SOUND_DURATION");

    private final String realName;

    TechnicalFacetType(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

}
