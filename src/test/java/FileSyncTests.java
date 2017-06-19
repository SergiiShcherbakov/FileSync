import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Sergii Shcherbakov on 19.06.2017.
 */
public class FileSyncTests {

    String soursDir = "H:\\Juja\\projects\\synch\\Test\\Test3";
    String destDir = "H:\\Juja\\projects\\synch\\Test\\Test2";

    @Test
    public void setData() throws IOException {
        Path sours = Paths.get(soursDir);
        Files.createDirectories(sours, null );
        sours = Paths.get(soursDir + "\\test1");
        Files.createDirectories(sours, null );
        sours = Paths.get(soursDir + "\\test2");
        Files.createDirectories(sours, null );
        sours = Paths.get(soursDir + "\\test2\\test.txt");
        Files.createFile(sours, null );
    }



}
