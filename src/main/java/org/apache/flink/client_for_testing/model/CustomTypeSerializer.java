package org.apache.flink.client_for_testing.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.common.typeutils.base.TypeSerializerSingleton;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.types.ByteValue;
import org.apache.flink.types.StringValue;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.StringParser;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomTypeSerializer extends TypeSerializerSingleton<CustomType> {

	public CustomTypeSerializer() {
	}

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public CustomType createInstance() {
		return new CustomType();
	}

	@Override
	public CustomType copy(CustomType from) {
		CustomType newInstance = new CustomType();
		newInstance.setF1(from.getF1());
		return newInstance;
	}

	@Override
	public CustomType copy(CustomType from, CustomType reuse) {
		reuse.setF1(from.getF1());
		return reuse;
	}

	@Override
	public int getLength() {
		return -1;
	}

	@Override
	public void serialize(CustomType record, DataOutputView target) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String recordStr = mapper.writeValueAsString(record);
		byte[] payload = recordStr.getBytes();

		target.writeInt(payload.length);
		target.write(payload);
	}

	@Override
	public CustomType deserialize(DataInputView source) throws IOException {
		int payloadLength = source.readInt();
		byte[] payload = new byte[payloadLength];
		source.readFully(payload);
		String fieldStr = new String(payload);

		ObjectMapper mapper = new ObjectMapper();
		CustomType customType = mapper.readValue(fieldStr, CustomType.class);
		return customType;
	}

	@Override
	public CustomType deserialize(CustomType reuse, DataInputView source) throws IOException {

		String fieldStr = source.readUTF();
		ObjectMapper mapper = new ObjectMapper();
		CustomType customType = mapper.readValue(fieldStr, CustomType.class);
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
