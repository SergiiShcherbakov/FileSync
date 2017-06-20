import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergii Shcherbakov on 19.06.2017.
 */
public class FileSyncTests {

    public static final String TEST_DIR_1 = "\\test1";
    public static final String TEST_DIR_2 = "\\test2";
    public static final String TEST_FILE_1 = "\\test2\\test.txt";
    String soursDir = "D:\\Test1";
    String destDir = "D:\\Test2";

    @Before
    public void setData() throws IOException {
        Path sours = Paths.get(soursDir);
        Files.createDirectories(sours );
        sours = Paths.get(soursDir + TEST_DIR_1);
        Files.createDirectories(sours );
        sours = Paths.get(soursDir + TEST_DIR_2);
        Files.createDirectories(sours);
        sours = Paths.get(soursDir + TEST_FILE_1);
        Files.createFile(sours );
    }

    @Test(expected = NullPointerException.class )
    public void Test_WithoutParameters() throws IOException {
        //given
        //when
        FileSync.main(new String[]{ });
        //then
    }

    @Test(expected = NullPointerException.class )
    public void Test_WithoutSecondParameter() throws IOException {
        //given
        //when
        FileSync.main(new String[]{soursDir });
        //then
    }

    @Test
    public void Test_WithoutDestDir() throws IOException {
        //given
        Path dest = Paths.get(destDir);
        Files.deleteIfExists(dest);
        //when
        FileSync.main(new String[]{soursDir, destDir});
        //then
        assertDirStructure(dest);
        assertCountFilesInDestDir();
    }

    @Test
    public void Test_WithEmptyDestDir() throws IOException {
        //given
        Path dest = Paths.get(destDir);
        Files.deleteIfExists(dest);
        Files.createDirectories(dest);
        //when
        FileSync.main(new String[]{soursDir, destDir});
        //then
        assertDirStructure(dest);
        assertCountFilesInDestDir();
    }

    @Test
    public void Test_WithAnotherFileInDestDir() throws IOException {
        //given
        Path dest = Paths.get(destDir);
        Files.deleteIfExists(dest);
        Files.createDirectories(dest);
        Path anotherFile = Paths.get(destDir + "\\AnotherFile.TXT");
        Files.deleteIfExists(anotherFile);
        Files.createFile(anotherFile);
        //when
        FileSync.main(new String[]{soursDir, destDir});
        //then
        assertDirStructure(dest);
        assertCountFilesInDestDir();
    }

    @Test
    public void Test_WithChangedFileInSours() throws IOException {
        //given
        FileSync.main(new String[]{soursDir, destDir});
        Path dest = Paths.get(destDir);

        byte[] testOutData =  new byte[]{0, 1, 2, 3};
        FileOutputStream out = new FileOutputStream(new File(soursDir + TEST_FILE_1));
        out.write(testOutData);
        out.close();
        //when
        FileSync.main(new String[]{soursDir, destDir});
        FileInputStream in = new FileInputStream(new File(destDir + TEST_FILE_1));
        byte[] testInData =  new byte[testOutData.length];
        in.read(testInData,0 , testOutData.length);
        in.close();
        //then
        assertEquals(Arrays.toString( testOutData) , Arrays.toString(testInData));
        assertDirStructure(dest);
        assertCountFilesInDestDir();
    }

    private void assertCountFilesInDestDir() {
        File file = new File(destDir);
        assertEquals(2, file.listFiles().length);
        file = new File(destDir + TEST_DIR_2);
        assertEquals(1, file.listFiles().length);
    }

    private void assertDirStructure(Path dest) {
        assertTrue( Files.exists(dest));
        dest = Paths.get(destDir + TEST_FILE_1);
        assertTrue( Files.exists(dest ));
        dest = Paths.get(destDir + TEST_DIR_2);
        assertTrue( Files.exists(dest ));
        dest = Paths.get(destDir + TEST_DIR_1);
        assertTrue( Files.exists(dest ));
    }

    @After
    public void deleteDataFromTests() throws IOException {
        //delete sours files
        Path sours = Paths.get(soursDir + TEST_FILE_1);
        Files.deleteIfExists(sours );
        sours = Paths.get(soursDir + TEST_DIR_1);
        Files.deleteIfExists(sours );
        sours = Paths.get(soursDir + TEST_DIR_2);
        Files.deleteIfExists(sours);
        sours = Paths.get(soursDir);
        Files.deleteIfExists(sours );
        //delete dest files
        Path dest = Paths.get(destDir + TEST_FILE_1);
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir + TEST_DIR_2);
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir + TEST_DIR_1);
        Files.deleteIfExists(dest );
        dest = Paths.get(destDir );
        Files.deleteIfExists(dest );
    }

}
