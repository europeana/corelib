package eu.europeana.corelib.edm.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jibx.runtime.ICharacterEscaper;

import java.io.IOException;
import java.io.Writer;

public class EuropeanaUTF8Escaper  implements ICharacterEscaper{

    private static final Logger LOG = LogManager.getLogger(EuropeanaUTF8Escaper.class);

    public static final EuropeanaUTF8Escaper s_instance = new EuropeanaUTF8Escaper();

    private EuropeanaUTF8Escaper() {
    }

    public void writeAttribute(String text, Writer writer) throws IOException {
        int mark = 0;

        for(int i = 0; i < text.length(); ++i) {
            char chr = text.charAt(i);
            if (chr == '"') {
                writer.write(text, mark, i - mark);
                mark = i + 1;
                writer.write("&quot;");
            } else if (chr == '&') {
                writer.write(text, mark, i - mark);
                mark = i + 1;
                writer.write("&amp;");
            } else if (chr == '<') {
                writer.write(text, mark, i - mark);
                mark = i + 1;
                writer.write("&lt;");
            } else if (chr == '>' && i > 2 && text.charAt(i - 1) == ']' && text.charAt(i - 2) == ']') {
                writer.write(text, mark, i - mark - 2);
                mark = i + 1;
                writer.write("]]&gt;");
            } else if (chr < ' ') {
                if (chr != '\t' && chr != '\n' && chr != '\r') {
                    LOG.error("Illegal Character code 0x{} : {} in attribute value text : {}", Integer.toHexString(chr), chr, text);
                }
            } else if (chr > '\ud7ff' && (chr < '\ue000' || chr == '\ufffe' || chr == '\uffff' || chr > 1114111)) {
                LOG.error("Illegal Character code 0x{} : {} in attribute value text : {}", Integer.toHexString(chr), chr, text);
            }
        }

        writer.write(text, mark, text.length() - mark);
    }

    public void writeContent(String text, Writer writer) throws IOException {
        int mark = 0;

        for(int i = 0; i < text.length(); ++i) {
            char chr = text.charAt(i);
            if (chr == '&') {
                writer.write(text, mark, i - mark);
                mark = i + 1;
                writer.write("&amp;");
            } else if (chr == '<') {
                writer.write(text, mark, i - mark);
                mark = i + 1;
                writer.write("&lt;");
            } else if (chr == '>' && i > 2 && text.charAt(i - 1) == ']' && text.charAt(i - 2) == ']') {
                writer.write(text, mark, i - mark - 2);
                mark = i + 1;
                writer.write("]]&gt;");
            } else if (chr < ' ') {
                if (chr != '\t' && chr != '\n' && chr != '\r') {
                    LOG.error("Illegal Character code 0x{} : {} in content text : {}", Integer.toHexString(chr), chr, text);
                }
            } else if (chr > '\ud7ff' && (chr < '\ue000' || chr == '\ufffe' || chr == '\uffff' || chr > 1114111)) {
                LOG.error("Illegal Character code 0x{} : {} in content text : {}", Integer.toHexString(chr), chr, text);
            }
        }

        writer.write(text, mark, text.length() - mark);
    }

    public void writeCData(String text, Writer writer) throws IOException {
        writer.write("<![CDATA[");

        for(int i = 0; i < text.length(); ++i) {
            char chr = text.charAt(i);
            if (chr == '>' && i > 2 && text.charAt(i - 1) == ']' && text.charAt(i - 2) == ']') {
                throw new IOException("Sequence \"]]>\" is not allowed within CDATA section text");
            }

            if (chr < ' ') {
                if (chr != '\t' && chr != '\n' && chr != '\r') {
                    LOG.error("Illegal Character code 0x{} : {} in CDATA section. text : {}", Integer.toHexString(chr), chr, text);
                }
            } else if (chr > '\ud7ff' && (chr < '\ue000' || chr == '\ufffe' || chr == '\uffff')) {
                LOG.error("Illegal Character code 0x{} : {} in CDATA section. text : {}", Integer.toHexString(chr), chr, text);
            }
        }

        writer.write(text);
        writer.write("]]>");
    }

    public static ICharacterEscaper getInstance() {
        return s_instance;
    }
}


