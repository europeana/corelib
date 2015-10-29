package eu.europeana.corelib.search.utils;

import eu.europeana.corelib.definitions.model.facets.inverseLogic.MediaTypeEncoding;
import eu.europeana.corelib.definitions.model.facets.inverseLogic.TagEncoding;
import eu.europeana.corelib.definitions.model.facets.logic.*;

import java.util.ArrayList;
import java.util.List;

public class FakeTagsUtils {


    static public List<Integer> imageFilterTags(final List<String> mimeTypes, final List<String> imageSizes,
                                                final List<Boolean> imageColors, final List<Boolean> imageGrayScales, final List<String> imageAspectRatios) {
        final List<Integer> filterTags = new ArrayList<>();
        Integer i = 0, j, k, l, m;

        do {
            String mimeType = null;
            if (mimeTypes.size() != 0) {
                mimeType = mimeTypes.get(i);
            }
            j = 0;
            do {
                String imageSize = null;
                if (imageSizes.size() != 0) {
                    imageSize = imageSizes.get(j);
                }
                k = 0;
                do {
                    Boolean imageColor = null;
                    if (imageColors.size() != 0) {
                        imageColor = imageColors.get(k);
                    }
                    l = 0;
                    do {
                        Boolean imageGrayScale = null;
                        if (imageGrayScales.size() != 0) {
                            imageGrayScale = imageGrayScales.get(l);
                        }
                        m = 0;
                        do {
                            String imageAspectRatio = null;
                            if (imageAspectRatios.size() != 0) {
                                imageAspectRatio = imageAspectRatios.get(m);
                            }

                            final Integer filterTag =
                                    calculateTag(1, mimeType, imageSize, imageColor, imageGrayScale,
                                            imageAspectRatio, null, null, null, null, null);
                            filterTags.add(filterTag);

                            m += 1;
                        } while (m < imageAspectRatios.size());

                        l += 1;
                    } while (l < imageGrayScales.size());

                    k += 1;
                } while (k < imageColors.size());

                j += 1;
            } while (j < imageSizes.size());

            i += 1;
        } while (i < mimeTypes.size());

        return filterTags;
    }

    static public List<Integer> soundFilterTags(List<String> mimeTypes, List<Boolean> soundHQs,
                                          List<String> soundDurations) {
        final List<Integer> filterTags = new ArrayList<>();

        Integer i = 0, j, k;

        do {
            String mimeType = null;
            if (mimeTypes.size() != 0) {
                mimeType = mimeTypes.get(i);
            }
            j = 0;
            do {
                Boolean soundHQ = null;
                if (soundHQs.size() != 0) {
                    soundHQ = soundHQs.get(j);
                }
                k = 0;
                do {
                    String soundDuration = null;
                    if (soundDurations.size() != 0) {
                        soundDuration = soundDurations.get(k);
                    }

                    final Integer filterTag =
                            calculateTag(2, mimeType, null, null, null, null, null, soundHQ,
                                    soundDuration, null, null);
                    filterTags.add(filterTag);

                    k += 1;
                } while (k < soundDurations.size());

                j += 1;
            } while (j < soundHQs.size());

            i += 1;
        } while (i < mimeTypes.size());

        return filterTags;
    }

    static public List<Integer> videoFilterTags(List<String> mimeTypes, List<Boolean> videoHQs,
                                          List<String> videoDurations) {
        final List<Integer> filterTags = new ArrayList<>();

        Integer i = 0, j, k;

        do {
            String mimeType = null;
            if (mimeTypes.size() != 0) {
                mimeType = mimeTypes.get(i);
            }
            j = 0;
            do {
                Boolean videoHQ = null;
                if (videoHQs.size() != 0) {
                    videoHQ = videoHQs.get(j);
                }
                k = 0;
                do {
                    String videoDuration = null;
                    if (videoDurations.size() != 0) {
                        videoDuration = videoDurations.get(k);
                    }

                    final Integer filterTag =
                            calculateTag(3, mimeType, null, null, null, null, null, null, null, videoHQ,
                                    videoDuration);
                    filterTags.add(filterTag);

                    k += 1;
                } while (k < videoDurations.size());

                j += 1;
            } while (j < videoHQs.size());

            i += 1;
        } while (i < mimeTypes.size());

        return filterTags;
    }


    static public Integer calculateTag(Integer mediaType, String mimeType, String imageSize,
                                       Boolean imageColor, Boolean imageGrayScale,
                                       String imageAspectRatio, String imageColorPalette, Boolean soundHQ,
                                       String soundDuration, Boolean videoHQ, String videoDuration) {
        Integer tag = 0;

        if (mimeType != null) {
            mimeType = mimeType.toLowerCase();
        }
        if (imageSize != null) {
            imageSize = imageSize.toLowerCase();
        }
        if (imageAspectRatio != null) {
            imageAspectRatio = imageAspectRatio.toLowerCase();
        }

        if (imageColorPalette != null) {
            imageColorPalette = imageColorPalette.toUpperCase();
        }
        if (soundDuration != null) {
            soundDuration = soundDuration.toLowerCase();
        }
        if (videoDuration != null) {
            videoDuration = videoDuration.toLowerCase();
        }

        switch (mediaType) {
            case 1:
                tag = calculateImageTag(mimeType, imageSize, imageColor, imageGrayScale,
                        imageAspectRatio, imageColorPalette);
                break;
            case 2:
                tag = calculateSoundTag(mimeType, soundHQ, soundDuration);
                break;
            case 3:
                tag = calculateVideoTag(mimeType, videoHQ, videoDuration);
                break;
        }

        return tag;
    }

    static public Integer calculateImageTag(final String mimeType, final String imageSize,
                                            final Boolean imageColor, final Boolean imageGrayScale,
                                            final String imageAspectRatio, final String imageColorPalette) {
        ImageOrientation imageOrientation = null;
        if (imageAspectRatio != null) {
            if (imageAspectRatio.equals("portrait")) {
                imageOrientation = ImageOrientation.PORTRAIT;
            }
            if (imageAspectRatio.equals("landscape")) {
                imageOrientation = ImageOrientation.LANDSCAPE;
            }
        }

        final Integer mediaTypeCode = MediaTypeEncoding.IMAGE.getEncodedValue();
        final Integer mimeTypeCode = CommonTagExtractor.getMimeTypeCode(mimeType);
        final Integer fileSizeCode = ImageTagExtractor.getSizeCode(imageSize);
        final Integer colorSpaceCode = ImageTagExtractor.getColorSpaceCode(imageColor, imageGrayScale);
        final Integer aspectRatioCode = ImageTagExtractor.getAspectRatioCode(imageOrientation);
        final Integer colorCode = ImageTagExtractor.getColorCode(imageColorPalette);

        final Integer tag = mediaTypeCode |
                mimeTypeCode << TagEncoding.MIME_TYPE.getBitPos() |
                fileSizeCode << TagEncoding.IMAGE_SIZE.getBitPos() |
                colorSpaceCode << TagEncoding.IMAGE_COLOURSPACE.getBitPos() |
                aspectRatioCode << TagEncoding.IMAGE_ASPECTRATIO.getBitPos() |
                colorCode << TagEncoding.IMAGE_COLOUR.getBitPos();

        return tag;
    }

    static public Integer calculateSoundTag(final String mimeType, final Boolean soundHQ,
                                            final String duration) {
        final Integer mediaTypeCode = MediaTypeEncoding.SOUND.getEncodedValue();
        final Integer mimeTypeCode = CommonTagExtractor.getMimeTypeCode(mimeType);
        final Integer qualityCode = SoundTagExtractor.getQualityCode(soundHQ);
        final Integer durationCode = SoundTagExtractor.getDurationCode(duration);

        return mediaTypeCode |
                mimeTypeCode << TagEncoding.MIME_TYPE.getBitPos() |
                qualityCode << TagEncoding.SOUND_QUALITY.getBitPos() |
                durationCode << TagEncoding.SOUND_DURATION.getBitPos();
    }

    static public Integer calculateVideoTag(final String mimeType,
                                            final Boolean videoQuality, final String duration) {
        final Integer mediaTypeCode = MediaTypeEncoding.VIDEO.getEncodedValue();
        final Integer mimeTypeCode = CommonTagExtractor.getMimeTypeCode(mimeType);
        final Integer qualityCode = VideoTagExtractor.getQualityCode(videoQuality);
        final Integer durationCode = VideoTagExtractor.getDurationCode(duration);

        return mediaTypeCode |
                mimeTypeCode << TagEncoding.MIME_TYPE.getBitPos() |
                qualityCode << TagEncoding.VIDEO_QUALITY.getBitPos() |
                durationCode << TagEncoding.VIDEO_DURATION.getBitPos();
    }

}
