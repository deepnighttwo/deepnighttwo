package enumerationlearn;

import java.lang.instrument.ClassDefinition;

public class TestEnumApp {

	public static void main(String[] args) {

		String[] a = null;
		Object[] b = a;
		ClassDefinition asdsdff;
		TestEnum asdf = TestEnum.STATUS1;
		System.out.println(asdf.getValue());
		switch (TestEnum.STATUS1) {
		case STATUS1:
			System.out.println();
		}
	}
}
