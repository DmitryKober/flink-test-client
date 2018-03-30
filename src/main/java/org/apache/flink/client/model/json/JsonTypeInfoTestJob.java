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
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.ParserFactory;

public class JsonTypeInfoTestJob {

	public static void main(String[] args) throws Exception {
        TypeInformation<CustomJsonType<NestedCustomJsonType>> of = TypeInformation.of(new TypeHint<CustomJsonType<NestedCustomJsonType>>(){});


        ParserFactory<CustomJsonType<NestedCustomJsonType>> factory = new ParserFactory<CustomJsonType<NestedCustomJsonType>>() {
            @Override
            public FieldParser<CustomJsonType<NestedCustomJsonType>> create(Class<? extends FieldParser<CustomJsonType<NestedCustomJsonType>>> parserType) {
                return new CustomJsonTypeStringParser<>(new TypeReference<CustomJsonType<NestedCustomJsonType>>() {});
            }
        };

        Class<CustomJsonType<NestedCustomJsonType>> type = (Class<CustomJsonType<NestedCustomJsonType>>) of.getTypeClass();
        Class<FieldParser<CustomJsonType<NestedCustomJsonType>>> customTypeStringParserClass = (Class<FieldParser<CustomJsonType<NestedCustomJsonType>>>) (Class<?>) CustomJsonTypeStringParser.class;
        FieldParser.registerCustomParser(
                type,
                customTypeStringParserClass,
                factory
        );

		CsvReader csvReader = new CsvReader("C:\\Users\\Dmitrii_Kober\\Projects\\flink-starter\\flink-test-client\\src\\main\\resources\\custom_json_type_field.csv", ExecutionEnvironment.getExecutionEnvironment());
		csvReader.fieldDelimiter(",");
        csvReader.parseQuotedStrings('\'');

        TupleTypeInfo<Tuple3<Integer, String, CustomJsonType<NestedCustomJsonType>>> tti = new TupleTypeInfo<>(
                BasicTypeInfo.INT_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO,
                TypeInformation.of(new TypeHint<CustomJsonType<NestedCustomJsonType>>(){})
        );
        DataSource<ResultContainer> tuple3DataSource = csvReader.tupleType(ResultContainer.class);
        tuple3DataSource.print();


        DataSource<Tuple3<Integer, String, CustomJsonType>> dataSource = csvReader.types(int.class, String.class, CustomJsonType.class);
		dataSource.print();
        System.out.println();
    }
}
