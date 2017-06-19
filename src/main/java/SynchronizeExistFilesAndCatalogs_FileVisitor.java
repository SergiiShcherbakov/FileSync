import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Sergii Shcherbakov on 19.06.2017.
 */
class SynchronizeExistFilesAndCatalogs_FileVisitor extends SimpleFileVisitor {
    Path destDir;
    Path soursDir;

    public SynchronizeExistFilesAndCatalogs_FileVisitor(Path soursDir, Path destDir) {
        this.soursDir = soursDir;
        this.destDir = destDir;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return copyIfDifferent(dir);
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        return copyIfDifferent(file );
    }

    public FileVisitResult copyIfDifferent(Object path ){
        Path soursFile = (Path) path;
        Path newPath = destDir.resolve(soursDir.relativize(soursFile));
        try {
            if (Files.exists(newPath) && ( Files.size(newPath) == Files.size(soursFile) || Files.isDirectory(newPath) )) {
                return FileVisitResult.CONTINUE;
            } else {
                Files.copy(soursFile, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }
}