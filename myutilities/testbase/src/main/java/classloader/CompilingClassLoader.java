/**
 * This source file is part of project littleaccountbook.
 * This project is under GNU General Public License v2.
 * This Project is a C/S account book.
 * Swing is used to create the GUI of this project and Java DB is used to store data in local machine.
 * Full source code of this project is available at http://littleaccountbook.googlecode.com/svn/trunk/ littleaccountbook-read-only
 * 
 * @author  Moon Zang
 * 
 */

package classloader;

//$Id: CompilingClassLoader.java 12 2010-01-12 08:18:50Z deepnighttwo $

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/*

 A CompilingClassLoader compiles your Java source on-the-fly.  It checks
 for nonexistent .class files, or .class files that are older than their
 corresponding source code.

 */

public class CompilingClassLoader extends ClassLoader {

    String baseDir = "D:/mymise/projects/DeepNightTwoUtilities/Test/src/";

    // Given a filename, read the entirety of that file from disk
    // and return it as a byte array.
    private byte[] getBytes(String filename) throws IOException {
        // Find out the length of the file
        File file = new File(filename);
        long len = file.length();

        // Create an array that's just the right size for the file's
        // contents
        byte raw[] = new byte[(int) len];

        // Open the file
        FileInputStream fin = new FileInputStream(file);

        // Read all of it into the array; if we don't get all,
        // then it's an error.
        int r = fin.read(raw);
        if (r != len)
            throw new IOException("Can't read all, " + r + " != " + len);

        // Don't forget to close the file!
        fin.close();

        // And finally return the file contents as an array
        return raw;
    }

    // Spawn a process to compile the java source code file
    // specified in the 'javaFile' parameter. Return a true if
    // the compilation worked, false otherwise.
    private boolean compile(String javaFile) throws IOException {
        // Let the user know what's going on
        System.out.println("CCL: Compiling " + javaFile + "...");

        // Start up the compiler
        Process p = Runtime.getRuntime().exec("javac " + javaFile, null,
                new File(baseDir));

        // Wait for it to finish running
        try {
            p.waitFor();
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
        int ch = 0;
        while ((ch = p.getErrorStream().read()) != -1) {
            System.out.print((char) ch);
        }

        // Check the return code, in case of a compilation error
        int ret = p.exitValue();

        // Tell whether the compilation worked
        return ret == 0;
    }

    // The heart of the ClassLoader -- automatically compile
    // source as necessary when looking for class files
    @SuppressWarnings("unchecked")
    public Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException {

        // Our goal is to get a Class object
        Class clas = null;

        System.err.println("findLoadedClass: " + name);

        // First, see if we've already dealt with this one
        clas = findLoadedClass(name);

        // Create a pathname from the class name
        // E.g. java.lang.Object => java/lang/Object
        String fileStub = baseDir + name.replace('.', '/');

        // Build objects pointing to the source code (.java) and object
        // code (.class)
        String javaFilename = fileStub + ".java";
        String classFilename = fileStub + ".class";

        File javaFile = new File(javaFilename);
        File classFile = new File(classFilename);

        // System.out.println( "j "+javaFile.lastModified()+" c "+
        // classFile.lastModified() );

        // First, see if we want to try compiling. We do if (a) there
        // is source code, and either (b0) there is no object code,
        // or (b1) there is object code, but it's older than the source

        // System.out.println(javaFile.exists());
        // System.out.println(classFile.exists());
        if (javaFile.exists()
                && (!classFile.exists() || javaFile.lastModified() > classFile
                        .lastModified())) {

            try {
                // Try to compile it. If this doesn't work, then
                // we must declare failure. (It's not good enough to use
                // and already-existing, but out-of-date, classfile)
                if (!compile(name.replace('.', '/') + ".java")
                        || !classFile.exists()) {
                    throw new ClassNotFoundException("Compile failed: "
                            + javaFilename);
                }
            } catch (IOException ie) {

                // Another place where we might come to if we fail
                // to compile
                throw new ClassNotFoundException(ie.toString());
            }
        }

        // Let's try to load up the raw bytes, assuming they were
        // properly compiled, or didn't need to be compiled
        try {

            // read the bytes
            byte raw[] = getBytes(classFilename);

            // try to turn them into a class
            clas = defineClass(name, raw, 0, raw.length);
        } catch (IOException ie) {
            // This is not a failure! If we reach here, it might
            // mean that we are dealing with a class in a library,
            // such as java.lang.Object
        }

        // System.out.println( "defineClass: "+clas );

        // Maybe the class is in a library -- try loading
        // the normal way
        if (clas == null) {
            clas = findSystemClass(name);
        }

        // System.out.println( "findSystemClass: "+clas );

        // Resolve the class, if any, but only if the "resolve"
        // flag is set to true
        if (resolve && clas != null)
            resolveClass(clas);

        // If we still don't have a class, it's an error
        if (clas == null)
            throw new ClassNotFoundException(name);

        // Otherwise, return the class
        return clas;
    }
}
