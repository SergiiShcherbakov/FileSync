import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Sergii Shcherbakov on 19.06.2017.
 */
public class CleanRemovedFilesFromDestFileVisitor extends SimpleFileVisitor {
    private final Path soursPth;
    private final Path destPath;

    public CleanRemovedFilesFromDestFileVisitor(Path sours, Path dest) {
        this.soursPth = sours;
        this.destPath = dest;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return removeInDestDeletedInSours(dir);
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        return removeInDestDeletedInSours(file );
    }

    public FileVisitResult removeInDestDeletedInSours(Object path ){
        Path detectedDestPath = (Path) path;
        Path detectedSoursPath = soursPth.resolve(destPath.relativize(detectedDestPath));
        if (Files.exists( detectedSoursPath)  ) {
                return FileVisitResult.CONTINUE;
        } else {
            try {
                Files.delete(detectedDestPath);
                System.out.println(detectedDestPath + "was delete");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
