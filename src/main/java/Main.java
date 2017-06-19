import java.io.IOException;
import java.nio.file.*;

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
public class Main {
    public static void main(String[] args) {
       Path sours = Paths.get( "H:\\Juja\\projects\\synch\\Test\\Test1");
       Path dest = Paths.get( "H:\\Juja\\projects\\synch\\Test\\Test2");

        try {
            Files.walkFileTree(sours, new SynchronizeExistFilesAndCatalogs_FileVisitor(sours, dest));
            Files.walkFileTree(dest, new CleanRemovedFilesFromDestFileVisitor(sours, dest));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
