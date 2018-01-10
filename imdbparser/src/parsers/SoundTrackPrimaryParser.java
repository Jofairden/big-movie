package parsers;

import main.Box;
import main.ImdbUtils;
import sun.invoke.empty.Empty;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.*;

/*
	Authors: Jildert
 */
// Will parse to the following: track||composer
public final class SoundTrackPrimaryParser extends Parser {

    private String track;
    private boolean written;
    private final Map<Integer, String> tracks = new HashMap<>();
    private int lastKey;
    private Box<Integer> count;



    public SoundTrackPrimaryParser(String inputFile) {
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
                super.writeLine = String.format("%s||%s \n", track , line);
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
