package process;

import java.nio.file.Paths;

public class Util {
    public static String createFilePath(String... paths) {
        return Paths.get(Paths.get("").toAbsolutePath().toString(), paths).toString();
    }
}
