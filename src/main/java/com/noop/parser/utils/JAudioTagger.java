package com.noop.parser.utils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class JAudioTagger extends AbstractVariables {

    public static void getTag(String path) {
        try {
            AudioFile f = AudioFileIO.read(new File(path));
            Tag tag = f.getTag();
            AudioHeader audioHeader = f.getAudioHeader();

            System.out.println(tag.getFirst(FieldKey.ARTIST));
            System.out.println(tag.getFirst(FieldKey.TITLE));

        } catch (IOException | CannotReadException | ReadOnlyFileException | TagException | InvalidAudioFrameException e) {
            e.printStackTrace();
        }
    }


}
