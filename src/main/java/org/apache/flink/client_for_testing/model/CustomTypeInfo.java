package org.apache.flink.client_for_testing.model;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomTypeInfo extends TypeInformation<CustomType> {

	@Override
	public boolean isBasicType() {
		return false;
	}

	@Override
	public boolean isTupleType() {
		return false;
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	public int getTotalFields() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<CustomType> getTypeClass() {
		return CustomType.class;
	}

	@Override
	public boolean isKeyType() {
		return true;
	}

	@Override
	public TypeSerializer<CustomType> createSerializer(ExecutionConfig config) {
		return new CustomTypeSerializer();
	}

	@Override
	public String toString() {
		return CustomType.class.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomTypeInfo) {
			@SuppressWarnings("unchecked")
			CustomTypeInfo thatCustomTypeInfo = (CustomTypeInfo) obj;

			return thatCustomTypeInfo.canEqual(this);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return CustomType.class.hashCode();
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof CustomTypeInfo;
	}
}
