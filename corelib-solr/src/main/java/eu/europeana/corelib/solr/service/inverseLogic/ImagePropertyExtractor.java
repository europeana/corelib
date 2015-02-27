package eu.europeana.corelib.solr.service.inverseLogic;

import java.util.HashMap;
import java.util.Map;

public class ImagePropertyExtractor {

    private final static Map<Integer, String> hexColors = new HashMap<>();
    static {
        hexColors.put(0, "");
        hexColors.put(1, "#F0F8FF");
        hexColors.put(2, "#FAEBD7");
        hexColors.put(3, "#00FFFF");
        hexColors.put(4, "#7FFFD4");
        hexColors.put(5, "#F0FFFF");
        hexColors.put(6, "#F5F5DC");
        hexColors.put(7, "#FFE4C4");
        hexColors.put(8, "#000000");
        hexColors.put(9, "#FFEBCD");
        hexColors.put(10, "#0000FF");
        hexColors.put(11, "#8A2BE2");
        hexColors.put(12, "#A52A2A");
        hexColors.put(13, "#DEB887");
        hexColors.put(14, "#5F9EA0");
        hexColors.put(15, "#7FFF00");
        hexColors.put(16, "#D2691E");
        hexColors.put(17, "#FF7F50");
        hexColors.put(18, "#6495ED");
        hexColors.put(19, "#FFF8DC");
        hexColors.put(20, "#DC143C");
        hexColors.put(21, "#00008B");
        hexColors.put(22, "#008B8B");
        hexColors.put(23, "#B8860B");
        hexColors.put(24, "#A9A9A9");
        hexColors.put(25, "#006400");
        hexColors.put(26, "#BDB76B");
        hexColors.put(27, "#8B008B");
        hexColors.put(28, "#556B2F");
        hexColors.put(29, "#FF8C00");
        hexColors.put(30, "#9932CC");
        hexColors.put(31, "#8B0000");
        hexColors.put(32, "#E9967A");
        hexColors.put(33, "#8FBC8F");
        hexColors.put(34, "#483D8B");
        hexColors.put(35, "#2F4F4F");
        hexColors.put(36, "#00CED1");
        hexColors.put(37, "#9400D3");
        hexColors.put(38, "#FF1493");
        hexColors.put(39, "#00BFFF");
        hexColors.put(40, "#696969");
        hexColors.put(41, "#1E90FF");
        hexColors.put(42, "#B22222");
        hexColors.put(43, "#FFFAF0");
        hexColors.put(44, "#228B22");
        hexColors.put(45, "#FF00FF");
        hexColors.put(46, "#DCDCDC");
        hexColors.put(47, "#F8F8FF");
        hexColors.put(48, "#FFD700");
        hexColors.put(49, "#DAA520");
        hexColors.put(50, "#808080");
        hexColors.put(51, "#008000");
        hexColors.put(52, "#ADFF2F");
        hexColors.put(53, "#F0FFF0");
        hexColors.put(54, "#FF69B4");
        hexColors.put(55, "#CD5C5C");
        hexColors.put(56, "#4B0082");
        hexColors.put(57, "#FFFFF0");
        hexColors.put(58, "#F0E68C");
        hexColors.put(59, "#E6E6FA");
        hexColors.put(60, "#FFF0F5");
        hexColors.put(61, "#7CFC00");
        hexColors.put(62, "#FFFACD");
        hexColors.put(63, "#ADD8E6");
        hexColors.put(64, "#F08080");
        hexColors.put(65, "#E0FFFF");
        hexColors.put(66, "#FAFAD2");
        hexColors.put(67, "#D3D3D3");
        hexColors.put(68, "#90EE90");
        hexColors.put(69, "#FFB6C1");
        hexColors.put(70, "#FFA07A");
        hexColors.put(71, "#20B2AA");
        hexColors.put(72, "#87CEFA");
        hexColors.put(73, "#778899");
        hexColors.put(74, "#B0C4DE");
        hexColors.put(75, "#FFFFE0");
        hexColors.put(76, "#00FF00");
        hexColors.put(77, "#32CD32");
        hexColors.put(78, "#FAF0E6");
        hexColors.put(79, "#800000");
        hexColors.put(80, "#66CDAA");
        hexColors.put(81, "#0000CD");
        hexColors.put(82, "#BA55D3");
        hexColors.put(83, "#9370DB");
        hexColors.put(84, "#3CB371");
        hexColors.put(85, "#7B68EE");
        hexColors.put(86, "#00FA9A");
        hexColors.put(87, "#48D1CC");
        hexColors.put(88, "#C71585");
        hexColors.put(89, "#191970");
        hexColors.put(90, "#F5FFFA");
        hexColors.put(91, "#FFE4E1");
        hexColors.put(92, "#FFE4B5");
        hexColors.put(93, "#FFDEAD");
        hexColors.put(94, "#000080");
        hexColors.put(95, "#FDF5E6");
        hexColors.put(96, "#808000");
        hexColors.put(97, "#6B8E23");
        hexColors.put(98, "#FFA500");
        hexColors.put(99, "#FF4500");
        hexColors.put(100, "#DA70D6");
        hexColors.put(101, "#EEE8AA");
        hexColors.put(102, "#98FB98");
        hexColors.put(103, "#AFEEEE");
        hexColors.put(104, "#DB7093");
        hexColors.put(105, "#FFEFD5");
        hexColors.put(106, "#FFDAB9");
        hexColors.put(107, "#CD853F");
        hexColors.put(108, "#FFC0CB");
        hexColors.put(109, "#DDA0DD");
        hexColors.put(110, "#B0E0E6");
        hexColors.put(111, "#800080");
        hexColors.put(112, "#FF0000");
        hexColors.put(113, "#BC8F8F");
        hexColors.put(114, "#4169E1");
        hexColors.put(115, "#8B4513");
        hexColors.put(116, "#FA8072");
        hexColors.put(117, "#F4A460");
        hexColors.put(118, "#2E8B57");
        hexColors.put(119, "#FFF5EE");
        hexColors.put(120, "#A0522D");
        hexColors.put(121, "#C0C0C0");
        hexColors.put(122, "#87CEEB");
        hexColors.put(123, "#6A5ACD");
        hexColors.put(124, "#708090");
        hexColors.put(125, "#FFFAFA");
        hexColors.put(126, "#00FF7F");
        hexColors.put(127, "#4682B4");
        hexColors.put(128, "#D2B48C");
        hexColors.put(129, "#008080");
        hexColors.put(130, "#D8BFD8");
        hexColors.put(131, "#FF6347");
        hexColors.put(132, "#40E0D0");
        hexColors.put(133, "#EE82EE");
        hexColors.put(134, "#F5DEB3");
        hexColors.put(135, "#FFFFFF");
        hexColors.put(136, "#F5F5F5");
        hexColors.put(137, "#FFFF00");
        hexColors.put(138, "#9ACD32");
    }

    public static String getSize(Integer tag) {
        final Integer sizeCode = (tag<<7)>>19;

        switch (sizeCode) {
            case 1: return "small";
            case 2: return "medium";
            case 3: return "large";
            case 4: return "extra_large";

            default: return "";
        }
    }

    public static String getColorSpace(Integer tag) {
        final Integer colorSpaceCode = (tag<<7)>>17;

        switch (colorSpaceCode) {
            case 1: return "sRGB";
            case 2: return "grayscale";
            case 3: return "CMYK";

            default: return "";
        }
    }

    public static String getAspectRatio(Integer tag) {
        final Integer aspectRatioCode = (tag<<7)>>15;

        switch (aspectRatioCode) {
            case 1: return "Landscape";
            case 2: return "Portrait";

            default: return "";
        }
    }

    public static String getColor(Integer tag) {
        final Integer colorCode = (tag<<7)>>7;

        return hexColors.get(colorCode)!=null?hexColors.get(colorCode):"";
    }
}
