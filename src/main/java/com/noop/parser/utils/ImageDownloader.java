package com.noop.parser.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.noop.parser.utils.DateTimeUtil.getDatetimeStamp;

public class ImageDownloader extends AbstractImages {

    public static void saveImageFromURLLocally(String url, String saveTo, String newFilename) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(saveTo + File.separatorChar + newFilename + getDatetimeStamp() + JPEG));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getBytesFromURL(String url) {
        try {
            InputStream in = new URL(url).openStream();
            return in;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
