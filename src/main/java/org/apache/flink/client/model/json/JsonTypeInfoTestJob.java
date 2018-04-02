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

package org.apache.flink.client.model.json;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.ParserFactory;

public class JsonTypeInfoTestJob {

	public static void main(String[] args) throws Exception {
        ParserFactory<CustomJsonType<NestedCustomJsonType>> factory = new ParserFactory<CustomJsonType<NestedCustomJsonType>>() {
            @Override
            public Class<? extends FieldParser<CustomJsonType<NestedCustomJsonType>>> getParserType() {
                Class<FieldParser<CustomJsonType<NestedCustomJsonType>>> customTypeStringParserClass =
                        (Class<FieldParser<CustomJsonType<NestedCustomJsonType>>>) (Class<?>) CustomJsonTypeStringParser.class;
                return customTypeStringParserClass;
            }

            @Override
            public FieldParser<CustomJsonType<NestedCustomJsonType>> create() {
                return new CustomJsonTypeStringParser<>(new TypeReference<CustomJsonType<NestedCustomJsonType>>() {});
            }
        };

        TypeInformation<CustomJsonType<NestedCustomJsonType>> typeInfo = TypeInformation.of(new TypeHint<CustomJsonType<NestedCustomJsonType>>(){});
        Class<CustomJsonType<NestedCustomJsonType>> type = typeInfo.getTypeClass();

        FieldParser.registerCustomParser(type,factory);

		CsvReader csvReader = new CsvReader("C:\\Users\\Dmitrii_Kober\\Projects\\flink-starter\\flink-test-client\\src\\main\\resources\\custom_json_type_field.csv", ExecutionEnvironment.getExecutionEnvironment());
		csvReader.fieldDelimiter(",");
        csvReader.parseQuotedStrings('\'');

        DataSource<ResultContainer> tuple3DataSource = csvReader.tupleType(ResultContainer.class);
        tuple3DataSource.print();

    }
}
