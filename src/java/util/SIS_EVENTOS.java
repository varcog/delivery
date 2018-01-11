package util;

import java.io.UnsupportedEncodingException;

public class SIS_EVENTOS {

    public SIS_EVENTOS() {
    }

    public String decodeUTF8(String text) throws UnsupportedEncodingException {
        return new String(text.getBytes("ISO-8859-15"), "UTF-8");
    }
}
