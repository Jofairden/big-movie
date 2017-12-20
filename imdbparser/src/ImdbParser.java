import java.io.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class ImdbParser {

    private String filePath;

    public ImdbParser() {
        filePath = new File("").getAbsolutePath();
    }

    public long streamFile(String path, BiConsumer<String, Writer> func) throws IOException {

        ZonedDateTime dt = ZonedDateTime.now();

        FileInputStream inputStream = null;
        Scanner sc = null;

        Writer writer = null;

        try {
            inputStream = new FileInputStream(filePath + "\\data\\raw\\" + path);
            sc = new Scanner(inputStream, "UTF-8");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath + "\\data\\parsed\\" + path), "ASCII"));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                func.accept(line, writer);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

        ZonedDateTime dt2 = ZonedDateTime.now();

        return Duration.between(dt, dt2).getSeconds();
    }
}
