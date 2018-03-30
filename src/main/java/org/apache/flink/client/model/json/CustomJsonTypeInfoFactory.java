package org.apache.flink.client.model.json;

import org.apache.flink.api.common.typeinfo.TypeInfoFactory;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */

public class CustomJsonTypeInfoFactory extends TypeInfoFactory<CustomJsonType>{

	@Override
	public TypeInformation<CustomJsonType> createTypeInfo(Type t, Map<String, TypeInformation<?>> genericParameters) {
		return new CustomJsonTypeInfo();
	}
}
