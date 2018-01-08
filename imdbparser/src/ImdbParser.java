import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
            sc = new Scanner(inputStream, StandardCharsets.ISO_8859_1.name());
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath + "\\data\\parsed\\" + path.replace("list", "csv")), StandardCharsets.UTF_8));

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

    public long streamFileNoWrite(String path, Consumer<String> func) throws IOException {

        ZonedDateTime dt = ZonedDateTime.now();

        FileInputStream inputStream = null;
        Scanner sc = null;

        Writer writer = null;

        try {
            inputStream = new FileInputStream(filePath + "\\data\\raw\\" + path);
            sc = new Scanner(inputStream, StandardCharsets.ISO_8859_1.name());

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                func.accept(line);
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
        }

        ZonedDateTime dt2 = ZonedDateTime.now();

        return Duration.between(dt, dt2).getSeconds();
    }

//    private BufferedReader getFileReader(final String file) throws IOException {
//        BufferedReader fileReader;
//        // Support compressed files
//        if (file.endsWith(".gz")) {
//            fileReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.ISO_8859_1));
//        } else if (file.endsWith(".zip")) {
//            fileReader = new BufferedReader(new InputStreamReader(new ZipInputStream(new FileInputStream(file)), StandardCharsets.ISO_8859_1));
//        } else {
//            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
//        }
//
//        return fileReader;
//    }
}
