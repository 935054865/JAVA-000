package Week_01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Question2 extends ClassLoader {


    private static final String CLASS_PATH = "Hello.xlass";
    private static final String CLASS_NAME = "Hello";

    public static void main(String[] args) {
        Class<?> helloClass = null;
        try {
            helloClass = new Question2().findClass(CLASS_NAME);
            Object hello = helloClass.newInstance();
            helloClass.getMethod("hello").invoke(hello);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {

        byte[] classBuffer = getClassBuffer(CLASS_PATH);
        if (classBuffer == null) {
            throw new ClassNotFoundException("File Not Found");
        }
        byte[] transferClassBuffer = transferBytes(classBuffer);

        return defineClass(className, transferClassBuffer, 0, transferClassBuffer.length);
    }

    /**
     * 根据根据类路径获取文件流
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static byte[] getClassBuffer(String classPath) {
        try {
            String path = Question2.class.getResource(classPath).getPath();
            if (path == null) {
                return null;
            }
            File file = new File(path);
            int fileSize = (int) file.length();
            byte[] buffer = new byte[fileSize];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buffer);
            fileInputStream.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 255-x 转换类bytes
     * @param classBytes
     * @return
     */
    private static byte[] transferBytes(byte[ ] classBytes) {

        for (int i = 0; i < classBytes.length; i++) {
            classBytes[i] = (byte) (255-classBytes[i]);
        }
        return classBytes;
    }

}
