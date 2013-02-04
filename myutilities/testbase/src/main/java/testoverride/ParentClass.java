package testoverride;
public class ParentClass {

    public String getName() {
        return "super cla撒旦放";
    }

    public int getN() {
        return 1;
    }

    public void w(Object obj) {
        System.out.println("asdf");
    }

    public void w(String s) {
        System.out.println("EEEEEEEEEEEEEEEEE");
    }

    public void printName() {
        System.out.println(getName());
    }

}
