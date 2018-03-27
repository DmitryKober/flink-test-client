package org.apache.flink.client_for_testing.model;

import org.apache.flink.api.common.typeinfo.TypeInfoFactory;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */

public class CustomTypeInfoFactory extends TypeInfoFactory<CustomType>{

	@Override
	public TypeInformation<CustomType> createTypeInfo(Type t, Map<String, TypeInformation<?>> genericParameters) {
		return new CustomTypeInfo();
	}
}
