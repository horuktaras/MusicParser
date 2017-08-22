package com.noop.parser.utils;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.musicbrainz.mp3.tagger.Tools.CoverArt;
import com.musicbrainz.mp3.tagger.Tools.Song;
import fm.last.musicbrainz.data.dao.impl.TrackDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.sound.midi.Track;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.noop.parser.utils.Mp3Lists.getAllMp3FilesFromDir;
import static com.noop.parser.utils.Mp3Lists.getMp3WithoutImage;

public class MusicBrainzAPI extends AbstractVariables {

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
                if (song.isFound() && song != null && song.getArtist() != null && song.getRelease() != null && song.getDuration() != null) {
                    CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());
                    System.out.println(String.format("Cover Art for %s - %s have been found.", song.getArtist(), song.getRelease()));
                    uuids.add(coverArt.getImageURL());
                    ImageDownloader.saveImageFromURLLocally(coverArt.getImageURL(),
                            "C:/Users/Noop/Desktop/Music",
                            String.format("%s - %s", song.getArtist(), song.getRelease().replace(":", "")));
                }
            }
            System.out.println(String.format("Files were uploaded %s, but has found just %s image(s).", mp3files.size(), uuids.size()));
        } catch (NoSuchElementException e) {
            e.fillInStackTrace();
        }
        return uuids;
    }

    public static void findAndStoreCoverArtForMp3(String path) {
        List<Mp3File> mp3files = getAllMp3FilesFromDir(path);
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
                    InputStream bytesFromURL = ImageDownloader.getBytesFromURL(coverArt.getImageURL());
                    id3v2Tag.setAlbumImage(IOUtils.toByteArray(bytesFromURL), "image/jpeg");
                    id3v2Tag.setArtist(song.getArtist());
                    id3v2Tag.setTitle(song.getRelease());
                    mp3file.save("C:/Users/Noop/Desktop/Music/" + String.format("%s - %s", song.getArtist(), song.getRelease() + MP3));
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



