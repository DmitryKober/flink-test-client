/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.client_for_testing.model;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.types.CustomTypeInfoRegister;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.StringParser;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomTypeStringParser extends FieldParser<CustomType> {

	private final StringParser stringParser = new StringParser();

	private CustomType lastResult;

	@Override
	protected int parseField(byte[] bytes, int startPos, int limit, byte[] delim, CustomType reuse) {
		Optional<TypeInformation> typeInfo = CustomTypeInfoRegister.getInstance().getTypeInfoFor(CustomType.class);
		if (typeInfo.isPresent()) {
			stringParser.parseField(bytes, startPos, limit, delim, null);
			TypeSerializer serializer = typeInfo.get().createSerializer(null);
			try {
				Object deserialize = serializer.deserialize(new DataInputView() {
					@Override
					public void skipBytesToRead(int numBytes) throws IOException {

					}

					@Override
					public int read(byte[] b, int off, int len) throws IOException {
						return 0;
					}

					@Override
					public int read(byte[] b) throws IOException {
						return 0;
					}

					@Override
					public void readFully(byte[] b) throws IOException {

					}

					@Override
					public void readFully(byte[] b, int off, int len) throws IOException {

					}

					@Override
					public int skipBytes(int n) throws IOException {
						return 0;
					}

					@Override
					public boolean readBoolean() throws IOException {
						return false;
					}

					@Override
					public byte readByte() throws IOException {
						return 0;
					}

					@Override
					public int readUnsignedByte() throws IOException {
						return 0;
					}

					@Override
					public short readShort() throws IOException {
						return 0;
					}

					@Override
					public int readUnsignedShort() throws IOException {
						return 0;
					}

					@Override
					public char readChar() throws IOException {
						return 0;
					}

					@Override
					public int readInt() throws IOException {
						return 0;
					}

					@Override
					public long readLong() throws IOException {
						return 0;
					}

					@Override
					public float readFloat() throws IOException {
						return 0;
					}

					@Override
					public double readDouble() throws IOException {
						return 0;
					}

					@Override
					public String readLine() throws IOException {
						return null;
					}

					@Override
					public String readUTF() throws IOException {
						return stringParser.getLastResult();
					}
				});
				lastResult = (CustomType) deserialize;
				return 1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	@Override
	public CustomType getLastResult() {
		return lastResult;
	}

	@Override
	public CustomType createValue() {
		return new CustomType();
	}
}
