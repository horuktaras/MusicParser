package com.noop.parser.utils;

import com.mpatric.mp3agic.Mp3File;
import com.musicbrainz.mp3.tagger.Tools.CoverArt;
import com.musicbrainz.mp3.tagger.Tools.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.noop.parser.utils.Mp3Lists.getAllMp3FilesFromDir;

public class MusicBrainzAPI extends AbstractImages {

    public static void main(String[] args) {
        Song song = Song.fetchSong(new File("C:\\Users\\thoruk\\Music\\Danny Howard - Bullet (Original Mix).mp3"));
        System.out.println(song.getRelease());
        System.out.println(song.getArtist());
        System.out.println(song.getArtistMBID());
        System.out.println(song.toJson());

        CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());

        System.out.println(coverArt.getImageURL());

 /*       List<String> allUUIDsFromDir = getAllUUIDsFromDir(ConsoleReader.readString());
        for (String anAllUUIDsFromDir : allUUIDsFromDir) {
            System.out.println("\n" + anAllUUIDsFromDir);
        }*/

    }

    public static List<String> getAllUUIDsFromDir(String path) {
        List<Mp3File> mp3files = getAllMp3FilesFromDir(path);
        List<String> uuids = new ArrayList<>();
        for (Mp3File mp3file : mp3files) {
            Song song = Song.fetchSong(new File(mp3file.getFilename()));
            if (song != null && song.isFound()) {
                CoverArt coverArt = CoverArt.fetchCoverArt(song.getReleaseGroupMBID());
                uuids.add(coverArt.getImageURL());

            }
        }
        return uuids;
    }
}



