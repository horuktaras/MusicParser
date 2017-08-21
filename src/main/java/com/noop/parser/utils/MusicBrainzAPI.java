package com.noop.parser.utils;

import com.mpatric.mp3agic.*;
import com.musicbrainz.mp3.tagger.Tools.CoverArt;
import com.musicbrainz.mp3.tagger.Tools.Song;
import fm.last.musicbrainz.data.dao.impl.TrackDaoImpl;
import org.apache.commons.io.IOUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.noop.parser.utils.Mp3Lists.getMp3WithoutImage;

public class MusicBrainzAPI extends AbstractImages {

    final static String MP3 = ".mp3";

    public static void main(String[] args) {
/*        Song song = Song.fetchSong(new File("C:\\Users\\thoruk\\Music\\Danny Howard - Bullet (Original Mix).mp3"));
        System.out.println(song.getRelease());
        System.out.println(song.getArtist());
        System.out.println(song.getArtistMBID());
        System.out.println(song.toJson());

        CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());

        System.out.println(coverArt.getImageURL());*/
/*
        List<String> allUUIDsFromDir = getAllUUIDsFromDir(ConsoleReader.readString());
        for (String anAllUUIDsFromDir : allUUIDsFromDir) {
            System.out.println("\n" + anAllUUIDsFromDir);
        }*/
        findAndStoreCoverArtForMp3(ConsoleReader.readString());

    }

    public static List<String> getAllUUIDsFromDir(String path) {
        List<Mp3File> mp3files = getMp3WithoutImage(path);
        List<String> uuids = new ArrayList<>();
        try {
            for (Mp3File mp3file : mp3files) {
                Song song = Song.fetchSong(new File(mp3file.getFilename()));
                ID3v2 id3v2Tag;
                if (mp3file.hasId3v2Tag()) {
                    id3v2Tag = mp3file.getId3v2Tag();
                } else {
                    // mp3 does not have an ID3v2 tag, let's create one..
                    id3v2Tag = new ID3v24Tag();
                    mp3file.setId3v2Tag(id3v2Tag);
                }
                if (song != null && song.isFound()) {
                    CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());
                    System.out.println(String.format("Cover Art for %s - %s have been found.", song.getArtist(), song.getRelease()));
                    uuids.add(coverArt.getImageURL());
                    //ImageDownloader.saveImageFromURLLocally(coverArt.getImageURL(), "C:/Users/thoruk/Music/images", "newImage");
                    InputStream bytesFromURL = ImageDownloader.getBytesFromURL(coverArt.getImageURL());
                    id3v2Tag.setAlbumImage(IOUtils.toByteArray(bytesFromURL), "image/jpeg");
                    mp3file.save("C:/Users/thoruk/Music/images/" + "newFile.mp3");
                    bytesFromURL.close();
                }
            }
            System.out.println(String.format("Files were uploaded %s, but has found just %s image(s).", mp3files.size(), uuids.size()));
        } catch (NoSuchElementException e) {
            e.fillInStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }
        return uuids;
    }

    public static void findAndStoreCoverArtForMp3(String path){
        List<Mp3File> mp3files = getMp3WithoutImage(path);
        try {
            for (Mp3File mp3file : mp3files) {
                Song song = Song.fetchSong(new File(mp3file.getFilename()));
                TrackDaoImpl trackDao = new TrackDaoImpl();
                ID3v2 id3v2Tag;
                if (mp3file.hasId3v2Tag()) {
                    id3v2Tag = mp3file.getId3v2Tag();
                } else {
                    // mp3 does not have an ID3v2 tag, let's create one..
                    id3v2Tag = new ID3v24Tag();
                    mp3file.setId3v2Tag(id3v2Tag);
                }
                if (song != null && song.isFound()) {
                    CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());
                    System.out.println(String.format("Cover Art for %s - %s have been found.", song.getArtist(), song.getRelease()));
                    //ImageDownloader.saveImageFromURLLocally(coverArt.getImageURL(), "C:/Users/thoruk/Music/images", "newImage");
                    InputStream bytesFromURL = ImageDownloader.getBytesFromURL(coverArt.getImageURL());
                    id3v2Tag.setAlbumImage(IOUtils.toByteArray(bytesFromURL), "image/jpeg");
                    id3v2Tag.setArtist(song.getArtist());
                    id3v2Tag.setTitle(song.getRelease());
                    song.toJson();
                    mp3file.save("C:/Users/thoruk/Music/images/" + String.format("%s - %s", song.getArtist(),song.getRelease() + MP3));
                    bytesFromURL.close();
                }
            }
        } catch (NoSuchElementException e) {
            e.fillInStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }
    }
}



