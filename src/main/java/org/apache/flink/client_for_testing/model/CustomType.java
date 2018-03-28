package org.apache.flink.client_for_testing.model;

import org.apache.flink.api.common.typeinfo.TypeInfo;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
@TypeInfo(CustomTypeInfoFactory.class)
public class CustomType {

	private String f1;
	private NestedCustomType f2;

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public NestedCustomType getF2() {
		return f2;
	}

	public void setF2(NestedCustomType f2) {
		this.f2 = f2;
	}
}
