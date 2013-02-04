package classinwhichjar;

import net.sf.cglib.asm.AnnotationVisitor;
import utils.ClassLoaderUtils;

public class ClassInWhichJar {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ClassLoaderUtils.getClassRealLocation(ClassInWhichJar.class);
        ClassLoaderUtils.getClassRealLocation(String.class);
        ClassLoaderUtils.getClassRealLocation(AnnotationVisitor.class);
    }
}
