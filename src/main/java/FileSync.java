import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Sergii Shcherbakov on 18.06.2017.
 * Реализовать синхронизацию директорий.
 * Консольная программа, которая на вход принимает 2 параметра source, destDir.
 * Должна состоять из 1 файла и запускаться java FileSynch.
 * Соответственно, клас должен быть без пакета (это плохая практика, не повторяйте на своих проектах - ведите структуру пакетов)4
 * Програма должна:
 *      Создать папку destDir, если такой нету.
 *      Скопировать в папку destDir все файлы из source, которые отсутствуют в destDir.
 *          При копировании соблюдать структуру директорий. Например, есть файл source/dir/file.txt.
 *          Если отсутствует destDir/dir/file.txt - необходимо его скопировать,
 *          так чтобы после операции появился файли destDir/dir/file.txt эквивалентный исходному.
 *      Скопировать все измененные файлы из папки source в папку destDir. Для простоты, изменение
 *          содержания файла следует учитывать его размер.
 *          Тоесть, если файл source/f1.txt отличается размером от файла destDir/f1.txt - заменить его.
 *      Удалить файл из destDir если он отсутствует в source, например, если файл был удален в source.
 *      Для решение следует использовать java.nio.file API которое появилось в java7.
 *          Разбор этого модуля предоставляется на самостоятельное рассмотрение,
 *          все вопросы следует задавать в соответствующем канале.
 *
 * Решение опубликовать в своем профиле Github, линк на решение опубликовать в канале slack
 * Внимание!
 *  В канале уже присутствуют разборы предыдущих решений - следует их пересмотреть и внести
 *          соответствующие правки в собственный код. Мы не гарантируем разбор решение каждого учасника,
 *          оно может быть сделаным выборочно.
 */

public class FileSync {
    final Path sours;
    final Path dest;

    private FileSync(String[] args) {
        if( args.length < 2 || args[1] == null ) {
            throw new NullPointerException("dest path did not input");
        }
        if( args.length < 1 || args[0] == null  ) {
            throw new NullPointerException("sours path did not input");
        }
        sours = Paths.get( args[0]);
        dest = Paths.get( args[1]);
    }

    public static void main(String[] args) {
        (new FileSync(args)).start();
    }

    private void start() {
        try {
            AddAllFilesToDest(sours, dest);
            CleanFilesInDest(sours, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CleanFilesInDest(Path sours, Path dest) throws IOException {
        Files.walkFileTree(dest, new SimpleFileVisitor(){
            @Override
            public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
                return removeInDestDeletedInSours(dir);
            }

            @Override
            public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
                return removeInDestDeletedInSours(file );
            }
        });
    }
    private FileVisitResult removeInDestDeletedInSours(Object path ) throws IOException {
        Path detectedDestPath = (Path) path;
        Path detectedSoursPath = sours.resolve(dest.relativize(detectedDestPath));
        if (Files.exists( detectedSoursPath)  ) {
            return FileVisitResult.CONTINUE;
        } else {
            Files.delete(detectedDestPath);
            System.out.println(detectedDestPath + "was delete");
        }
        return FileVisitResult.CONTINUE;
    }

    private  void AddAllFilesToDest(Path sours, Path dest) throws IOException {
        Files.walkFileTree(sours,  new SimpleFileVisitor(){
            @Override
            public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
                return copyIfDifferent(dir);
            }
            @Override
            public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
                return copyIfDifferent(file );
            }

        });
    }

    private FileVisitResult copyIfDifferent(Object path ) throws IOException {
        Path soursFile = (Path) path;
        Path newPath = dest.resolve(sours.relativize(soursFile));
        if (Files.exists(newPath) && ( Files.size(newPath) == Files.size(soursFile) || Files.isDirectory(newPath) )) {
            return FileVisitResult.CONTINUE;
        } else {
            Files.copy(soursFile, newPath, StandardCopyOption.REPLACE_EXISTING);
        }
        return FileVisitResult.CONTINUE;
    }
}
