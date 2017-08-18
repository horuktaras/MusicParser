package com.noop.parser.main;

import com.mpatric.mp3agic.*;
import com.noop.parser.utils.ConsoleReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass {

    static Logger logger;

    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {
/*        File dir = new File(ConsoleReader.readString());
        File[] files = dir.listFiles();
        Mp3File mp3File;
        ID3v1 id3v1Tag;
        ID3v2 id3v2Tag;
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().contains(".mp3")) {
                    mp3File = new Mp3File(file.getPath());
                    id3v1Tag = mp3File.getId3v1Tag();
                    id3v2Tag = mp3File.getId3v2Tag();
                    boolean isImageExists = !(id3v2Tag.getAlbumImage().length == 0);
                    System.out.println(String.format("\nFile Name: %s", file.getName()));
                    System.out.println(String.format("Artist: %s", id3v1Tag.getArtist()));
                    System.out.println(String.format("Title: %s", id3v1Tag.getTitle()));
                    System.out.println(String.format("Album: %s", id3v1Tag.getAlbum()));
                    System.out.println(String.format("Icon exists: %s", isImageExists));
                    System.out.println(String.format("Bitrate: %s", mp3File.getBitrate()));
                }
            }
        } else logger.log(Level.WARNING, "Path specified wrong or directory is empty.");*/


/*        for (Mp3File file : getAllMp3Files(ConsoleReader.readString())) {
            System.out.println(String.format("\nFile name: %s", file.getFilename()));
            System.out.println(String.format("Artist: %s", file.getId3v1Tag().getArtist()));
            System.out.println(String.format("Title: %s", file.getId3v1Tag().getTitle()));
            System.out.println(String.format("Bitrate: %s", file.getBitrate()));
        }

        for (Mp3File file : getMp3WithoutId3v1(getAllMp3Files(ConsoleReader.readString()))) {
            System.out.println(String.format("File without Id3v1 Tag: %s", file.getFilename()));
        }

        for (Mp3File file : getMp3WithoutId3v2(getAllMp3Files(ConsoleReader.readString()))) {
            System.out.println(String.format("File without Id3v1 Tag: %s", file.getFilename()));
        }*/


        String input = ConsoleReader.readString();
        MainClass mainClass = new MainClass();
        mainClass.copyTagsFromV2toV1(getMp3WithoutId3v1(getAllMp3Files(input)), getAllMp3Files(input));



        for (Mp3File file : getMp3WithoutId3v1(getAllMp3Files(ConsoleReader.readString()))) {
            System.out.println(String.format("File without Id3v1 Tag: %s", file.getFilename()));
        }

    }

    public static List<Mp3File> getAllMp3Files(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        Mp3File mp3File;
        List<Mp3File> mp3Files = new ArrayList<>();
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && (file.getName().contains(".mp3") || file.getName().contains(".flac"))) {
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

    public static List<Mp3File> getMp3WithoutId3v1(List<Mp3File> list) {
        List<Mp3File> emptyMp3Files = new ArrayList<>();
        for (Mp3File file : list) {
            if (file.getId3v1Tag().getArtist().isEmpty() || file.getId3v1Tag().getTitle().isEmpty()) {
                emptyMp3Files.add(file);
            }

        }
        return emptyMp3Files;
    }

    public static List<Mp3File> getMp3WithoutId3v2(List<Mp3File> list) {
        List<Mp3File> emptyMp3Files = new ArrayList<>();
        for (Mp3File file : list) {
            if (file.getId3v2Tag().getArtist().isEmpty() || file.getId3v2Tag().getTitle().isEmpty()) {
                emptyMp3Files.add(file);
            }

        }
        return emptyMp3Files;
    }

    public void copyTagsFromV2toV1(List<Mp3File> v1, List<Mp3File> v2) throws IOException, NotSupportedException {

        for (Mp3File emptyFile : v1) {
            for (Mp3File file : v2) {
                ID3v1 id3v1Tag;
                id3v1Tag = emptyFile.getId3v1Tag();
                if (emptyFile.getFilename().equals(file.getFilename())) {
                    String artist = file.getId3v2Tag().getArtist();
                    String title = file.getId3v2Tag().getTitle();
                    String album = file.getId3v2Tag().getAlbum();
                    if (artist != null){
                        id3v1Tag.setArtist(artist);
                    }
                    if (title != null){
                        id3v1Tag.setTitle(title);
                    }
                    if (album != null){
                        id3v1Tag.setAlbum(album);
                    }
                    emptyFile.save(String.format("%s - %s", artist, title));
                }
            }
        }
    }


}
