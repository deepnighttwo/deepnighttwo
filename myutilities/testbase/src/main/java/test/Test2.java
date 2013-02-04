package test;

public class Test2 {
	public static void main(String[] args) {
		String str1 = new String("a");
		String str2 = new String("a");
		String str3 ="a";
		String str4 = "a";
		
		System.out.println(str1 == str2);
		
		System.out.println(str3 == str4);

		
		A a = new A();
		B b = new B();
		a.func();
		b.func();
		System.out.println("a.i=" + a.i);
		System.out.println("b.i=" + b.i);
		A c = new B();
		c.func();
		System.out.println("c.i=" + c.i);
	}
}

class A {
	int i = 1;

	public void func() {
		System.out.println("func in A\t" + i);
	}
}

class B extends A {
	int i = 2;

	public void func() {
		System.out.println("func in B\t" + i);
	}

}
