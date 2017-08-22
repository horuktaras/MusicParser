package com.noop.parser.main;

import com.mpatric.mp3agic.Mp3File;
import com.musicbrainz.mp3.tagger.Tools.CoverArt;
import com.noop.parser.utils.ConsoleReader;
import com.noop.parser.utils.JAudioTagger;
import com.noop.parser.utils.Mp3agic;

import java.util.Map;
import java.util.logging.Logger;

import static com.noop.parser.utils.MusicBrainzAPI.findAndStoreCoverArtForMp3;
import static com.noop.parser.utils.MusicBrainzAPI.getAllUUIDsFromDir;
import static org.jaudiotagger.audio.SupportedFileFormat.MP3;

public class MainClass {

    static Logger logger;

    public static void main(String[] args) {
        //findAndStoreCoverArtForMp3(ConsoleReader.readString());
        //getAllUUIDsFromDir(ConsoleReader.readString());

/*        Map<Mp3File, CoverArt> map = Mp3agic.mapFilesWithCoverArts(ConsoleReader.readString());
        map.forEach((k,v)-> System.out.println(String.format(
                "Track: %s - %s, Cover Art link: %s",
                k.getId3v1Tag().getArtist(),
                k.getId3v1Tag().getTitle(),
                v.getImageURL())));*/

        JAudioTagger.getTag("C:\\Users\\Noop\\Desktop\\Music\\Albertor - Luma (Original Mix).mp3");
    }
}
