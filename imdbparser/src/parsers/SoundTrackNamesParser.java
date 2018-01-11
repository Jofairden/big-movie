package parsers;

import java.util.*;

/*
	Authors: Jildert, Daniel
 */
// Will parse to the following: track||composer
public final class SoundTrackNamesParser extends Parser {

    private String track;
    private boolean written;
    private final Map<Integer, String> tracks = new HashMap<>();
    private int lastKey;

    public SoundTrackNamesParser(String inputFile) {
        super(inputFile);
        track = "";
        written = false;
        lastKey = 0;
    }

    @Override
    public boolean canParse(String line) {
        return true;
    }

    @Override
    public void parseLine(String line) {
        if (written){

            lastKey = (track + line).hashCode();

            if (!tracks.containsKey(lastKey)){
                super.writeLine = String.format("%s||%s \n", track , line.trim());
                tracks.put((track + line).hashCode(), (track + line));
            }

            written = false;
        }
        else if (line.length() > 2 && line.charAt(0) != '#' && line.charAt(2) == '"' && line.charAt(0) == '-') {
            track =  line.substring(2);
            written = true;
        }
    }
}
