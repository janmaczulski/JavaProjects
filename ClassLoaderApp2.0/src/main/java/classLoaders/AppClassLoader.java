package classLoaders;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppClassLoader extends ClassLoader {

    private static final List<String> foundClassNames = new ArrayList<>();
    private static String loadFromDirPath;

    public AppClassLoader(String dirPath) {
        loadFromDirPath = dirPath;
    }

    public static List<String> returnListFileOfDir() throws Exception {

        Path path = Paths.get(loadFromDirPath);
        final File folder = new File(loadFromDirPath);
        File file;

        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {

            file = new File(loadFromDirPath + fileEntry.getName());
            if(getExtensionFile(fileEntry.getName()).equals("class")){
                foundClassNames.add(fileEntry.getName().substring(0,fileEntry.getName().lastIndexOf('.')));
            }
        }
        return foundClassNames;
    }
    public static String getExtensionFile(String fileName){
        char ch;
        int len;
        if(fileName==null ||
                (len = fileName.length())==0 ||
                (ch = fileName.charAt(len-1))=='/' || ch=='\\' ||
                ch=='.' )
            return "";
        int dotInd = fileName.lastIndexOf('.'),
                sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if( dotInd<=sepInd )
            return "";
        else
            return fileName.substring(dotInd+1).toLowerCase();
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println(name);
        Path classPath = Paths.get(loadFromDirPath+ "/" + name.replace('.', File.separatorChar) + ".class");
        System.out.println(classPath);
        if (Files.exists(classPath)){
            try {
                byte[] byteCode = Files.readAllBytes(classPath);
                return this.defineClass("classesToLoad." + name, byteCode, 0, byteCode.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        } else {
            throw new ClassNotFoundException(name);
        }
    }

}
