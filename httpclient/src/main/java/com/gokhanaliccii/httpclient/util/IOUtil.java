package com.gokhanaliccii.httpclient.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

    public static void pipe(InputStream is, OutputStream os) throws IOException {
        byte[] line = new byte[1024];
        int len = 0;
        while ((len = is.read(line)) > 0) {
            os.write(line, 0, len);
        }
    }

    public static void closeStreams(Closeable... streams) {
        if (streams != null) {
            for (Closeable stream : streams) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
