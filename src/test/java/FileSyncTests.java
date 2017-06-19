import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sergii Shcherbakov on 19.06.2017.
 */
public class FileSyncTests {

    String soursDir = "H:\\Juja\\projects\\synch\\Test\\Test3";
    String destDir = "H:\\Juja\\projects\\synch\\Test\\Test4";

    @Before
    public void setData() throws IOException {
        Path sours = Paths.get(soursDir);
        Files.createDirectories(sours );
        sours = Paths.get(soursDir + "\\test1");
        Files.createDirectories(sours );
        sours = Paths.get(soursDir + "\\test2");
        Files.createDirectories(sours);
        sours = Paths.get(soursDir + "\\test2\\test.txt");
        Files.createFile(sours );
    }


    @Test
    public void TestWithoutDestDir() throws IOException {
        //given
        Path dest = Paths.get(destDir);
        Files.deleteIfExists(dest);
        //when
        FileSync.main(new String[]{soursDir, destDir});
        //then
        assertTrue( Files.exists(dest));
        dest = Paths.get(destDir + "\\test2\\test.txt");
        assertTrue( Files.exists(dest ));
        dest = Paths.get(destDir + "\\test2");
        assertTrue( Files.exists(dest ));
        dest = Paths.get(destDir + "\\test1");
        assertTrue( Files.exists(dest ));
    }


    @After
    public void deleteDataFromTests() throws IOException {
        //delete sours files
        Path sours = Paths.get(soursDir + "\\test2\\test.txt");
        Files.deleteIfExists(sours );
        sours = Paths.get(soursDir + "\\test1");
        Files.deleteIfExists(sours );
        sours = Paths.get(soursDir + "\\test2");
        Files.deleteIfExists(sours);
        sours = Paths.get(soursDir);
        Files.deleteIfExists(sours );
        //delete dest files
        Path dest = Paths.get(destDir + "\\test2\\test.txt");
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir + "\\test2");
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir + "\\test1");
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir );
        Files.deleteIfExists(dest );
    }


}
