package com.noop.parser.utils;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp3Lists {

    static List<Mp3File> getAllMp3FilesFromDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        Mp3File mp3File;
        List<Mp3File> mp3Files = new ArrayList<>();
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && (file.getName().contains(".mp3"))) {
                        mp3File = new Mp3File(file.getPath());
                        mp3Files.add(mp3File);
                    }
                }
            }
        } catch (UnsupportedTagException | IOException | InvalidDataException e) {
            e.printStackTrace();
        }
        return mp3Files;
    }

    public static List<Mp3File> getMp3WithoutId3v1(String path) {
        List<Mp3File> allMp3Files = getAllMp3FilesFromDir(path);
        List<Mp3File> emptyMp3Files = new ArrayList<>();
        for (Mp3File file : allMp3Files) {
            if (file.getId3v1Tag().getArtist().isEmpty() || file.getId3v1Tag().getTitle().isEmpty()) {
                emptyMp3Files.add(file);
            }

        }
        return emptyMp3Files;
    }

    public static List<Mp3File> getMp3WithoutId3v2(String path) {
        List<Mp3File> allMp3Files = getAllMp3FilesFromDir(path);
        List<Mp3File> emptyMp3Files = new ArrayList<>();
        for (Mp3File file : allMp3Files) {
            if (file.getId3v2Tag().getArtist().isEmpty() || file.getId3v2Tag().getTitle().isEmpty()) {
                emptyMp3Files.add(file);
            }

        }
        return emptyMp3Files;
    }

    public static List<Mp3File> getMp3WithoutImage(String path) {
        List<Mp3File> allMp3Files = getAllMp3FilesFromDir(path);
        List<Mp3File> noImagesFiles = new ArrayList<>();
        for (Mp3File file : allMp3Files) {
            if (file.getId3v2Tag().getAlbumImage() == null) {
                noImagesFiles.add(file);
            }
        }
        if (noImagesFiles != null) {
            return noImagesFiles;
        } else throw new NullPointerException();
    }


}
