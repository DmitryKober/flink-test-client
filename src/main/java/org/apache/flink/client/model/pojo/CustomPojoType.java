package org.apache.flink.client.model.pojo;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomPojoType<T> {


	private String f1;
	private NestedCustomPojoType f2;
	private T f3;

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public NestedCustomPojoType getF2() {
		return f2;
	}

	public void setF2(NestedCustomPojoType f2) {
		this.f2 = f2;
	}

	public T getF3() {
		return f3;
	}

	public void setF3(T f3) {
		this.f3 = f3;
	}
}
