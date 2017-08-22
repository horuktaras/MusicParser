package com.noop.parser.utils;

import com.mpatric.mp3agic.Mp3File;
import com.musicbrainz.mp3.tagger.Tools.CoverArt;
import com.musicbrainz.mp3.tagger.Tools.Song;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.noop.parser.utils.Mp3Lists.getAllMp3FilesFromDir;

public class Mp3agic extends AbstractVariables {

    static Map<Mp3File, CoverArt> MUSIC_WITH_COVERS = new HashMap<>();

    public static Map<Mp3File, CoverArt> mapFilesWithCoverArts(String path) {
        List<Mp3File> mp3files = getAllMp3FilesFromDir(path);
        for (Mp3File mp3file : mp3files) {
            Song song = Song.fetchSong(new File(mp3file.getFilename()));
            if (song.isFound()) {
                MUSIC_WITH_COVERS.put(mp3file, CoverArt.fetchCoverArt(song.getReleaseGroupMBID()));
                System.out.println(String.format("Cover Art for %s - %s have been found.", song.getArtist(), song.getRelease()));
            }
        }
        return MUSIC_WITH_COVERS;
    }


}
