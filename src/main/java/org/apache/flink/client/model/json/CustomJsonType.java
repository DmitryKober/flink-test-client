package org.apache.flink.client.model.json;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomJsonType<T> {

	private String f1;
	private NestedCustomJsonType f2;
	private T f3;

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public NestedCustomJsonType getF2() {
		return f2;
	}

	public void setF2(NestedCustomJsonType f2) {
		this.f2 = f2;
	}

	public T getF3() {
		return f3;
	}

	public void setF3(T f3) {
		this.f3 = f3;
	}

	@Override
	public String toString() {
		return "CustomJsonType{" +
				"f1='" + f1 + '\'' +
				", f2=" + f2 +
				", f3=" + f3 +
				'}';
	}
}
