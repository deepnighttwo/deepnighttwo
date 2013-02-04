package enumerationlearn;

public enum TestEnum {
	STATUS1(1), STATUS2(2), STATUS3(3);

	private int value;

	public int getValue() {
		return value;
	}

	TestEnum(int value) {
		this.value = value;
	}

}