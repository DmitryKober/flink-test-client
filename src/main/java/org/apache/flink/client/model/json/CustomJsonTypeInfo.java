package org.apache.flink.client.model.json;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomJsonTypeInfo extends TypeInformation<CustomJsonType> {

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
		return 2;
	}

	@Override
	public int getTotalFields() {
		return 3;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<CustomJsonType> getTypeClass() {
		return CustomJsonType.class;
	}

	@Override
	public boolean isKeyType() {
		return true;
	}

	@Override
	public TypeSerializer<CustomJsonType> createSerializer(ExecutionConfig config) {
		return new CustomJsonTypeSerializer();
	}

	@Override
	public String toString() {
		return CustomJsonType.class.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomJsonTypeInfo) {
			@SuppressWarnings("unchecked")
			CustomJsonTypeInfo thatCustomTypeInfo = (CustomJsonTypeInfo) obj;

			return thatCustomTypeInfo.canEqual(this);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return CustomJsonType.class.hashCode();
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof CustomJsonTypeInfo;
	}
}
