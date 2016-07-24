package com.finicityclient.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class StringUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static String streamToString(InputStream inputStream, Charset charset) throws IOException {
        if (inputStream == null) {
            return null;
        }

        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        StringBuilder stringBuilder = new StringBuilder();
        Reader reader = new InputStreamReader(inputStream, charset);

        int size = reader.read(buffer, 0, buffer.length);
        while (size >= 0) {
            stringBuilder.append(buffer, 0, size);

            size = reader.read(buffer, 0, buffer.length);
        }

        return stringBuilder.toString();
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;

        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
