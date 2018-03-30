package org.apache.flink.client.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.common.typeutils.base.TypeSerializerSingleton;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.types.StringValue;

import java.io.IOException;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomJsonTypeSerializer extends TypeSerializerSingleton<CustomJsonType> {

	public CustomJsonTypeSerializer() {
	}

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public CustomJsonType createInstance() {
		return new CustomJsonType();
	}

	@Override
	public CustomJsonType copy(CustomJsonType from) {
		CustomJsonType newInstance = new CustomJsonType();
		newInstance.setF1(from.getF1());
		return newInstance;
	}

	@Override
	public CustomJsonType copy(CustomJsonType from, CustomJsonType reuse) {
		reuse.setF1(from.getF1());
		return reuse;
	}

	@Override
	public int getLength() {
		return -1;
	}

	@Override
	public void serialize(CustomJsonType record, DataOutputView target) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String recordStr = mapper.writeValueAsString(record);
		byte[] payload = recordStr.getBytes();

		target.writeInt(payload.length);
		target.write(payload);
	}

	@Override
	public CustomJsonType deserialize(DataInputView source) throws IOException {
		int payloadLength = source.readInt();
		byte[] payload = new byte[payloadLength];
		source.readFully(payload);
		String fieldStr = new String(payload);

		ObjectMapper mapper = new ObjectMapper();
		CustomJsonType customType = mapper.readValue(fieldStr, CustomJsonType.class);
		return customType;
	}

	@Override
	public CustomJsonType deserialize(CustomJsonType reuse, DataInputView source) throws IOException {

		String fieldStr = source.readUTF();
		ObjectMapper mapper = new ObjectMapper();
		CustomJsonType customType = mapper.readValue(fieldStr, CustomJsonType.class);
		reuse.setF1(customType.getF1());
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		StringValue.copyString(source, target);
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof StringSerializer;
	}
}
