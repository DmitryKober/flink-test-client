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

package org.apache.flink.client.model.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.StringParser;

import java.io.IOException;

/**
 * Created by Dmitrii_Kober on 3/27/2018.
 */
public class CustomPojoTypeStringParser extends FieldParser<CustomPojoType> {

	private final StringParser stringParser = new StringParser();
    {
        stringParser.enableQuotedStringParsing((byte)'\'');
    }

	private CustomPojoType lastResult;

	@Override
	protected int parseField(byte[] bytes, int startPos, int limit, byte[] delim, CustomPojoType reuse) {
        stringParser.parseField(bytes, startPos, limit, delim, null);
        String stringRepresentation = stringParser.getLastResult();

        ObjectMapper mapper = new ObjectMapper();
        try {
            lastResult = mapper.readValue(stringRepresentation, CustomPojoType.class);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
	}

	@Override
	public CustomPojoType getLastResult() {
		return lastResult;
	}

	@Override
	public CustomPojoType createValue() {
		return new CustomPojoType();
	}
}
