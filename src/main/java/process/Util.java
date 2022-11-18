package process;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Util {
    public static String createFilePath(String... paths) {
        return Paths.get(Paths.get("").toAbsolutePath().toString(), paths).toString();
    }

    public static int millisToSeconds(long n) {
        return (int) n / 1000;
    }

    public static void removeFilesFromTempFolder() {
        Arrays.stream(Objects.requireNonNull(new File(Util.createFilePath("temp")).listFiles())).forEach(File::delete);
    }
}
