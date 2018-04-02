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

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.ParserFactory;

public class PojoTypeInfoTestJob {

	public static void main(String[] args) throws Exception {
//        ParserFactory<CustomJsonType> factory = new ParserFactory<CustomJsonType>() {
//            @Override
//            public FieldParser<CustomJsonType> create(Class<? extends FieldParser<CustomJsonType>> parserType) {
//                return new CustomJsonTypeStringParser();
//            }
//        };

//        Class<FieldParser<CustomJsonType>> customTypeStringParserClass = (Class<FieldParser<CustomJsonType>>) (Class<?>) CustomJsonTypeStringParser.class;
//        FieldParser.registerCustomParser(
//                CustomJsonType.class,
//                customTypeStringParserClass,
//                factory
//        );


        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

        PojoTypeInfo<CustomPojoType<String>> pojoTypeInfo = (PojoTypeInfo<CustomPojoType<String>>) (PojoTypeInfo<?>) TypeExtractor.getForClass(CustomPojoType.class);
//        PojoTypeInfoWrapper<CustomPojoType> wrappedPojoTypeInfo = new PojoTypeInfoWrapper<>(pojoTypeInfo);

        NestedCustomPojoType nestedCustomPojo = new NestedCustomPojoType();
        nestedCustomPojo.setF21("f21_value");
        nestedCustomPojo.setF22(22);

        CustomPojoType customPojo = new CustomPojoType();
        customPojo.setF1("f1_value");
        customPojo.setF2(nestedCustomPojo);

        customPojo.setF3("abc");

        StringBufferOutputStream outBufferStream = new StringBufferOutputStream();
        DataOutputView out = new DataOutputViewStreamWrapper(outBufferStream);
        pojoTypeInfo.createSerializer(executionEnvironment.getConfig()).serialize(customPojo, out);
        String st = outBufferStream.toString();

        SerializationSchema serializationSchema = new SerializationSchema() {
            @Override
            public byte[] serialize(Object o) {
                return new byte[0];
            }
        };

        FieldParser.registerCustomParser(
                CustomPojoType.class,
                (Class<FieldParser<CustomPojoType>>) (Class<?>) CustomPojoTypeStringParser.class,
                new ParserFactory<CustomPojoType>() {
                    @Override
                    public FieldParser<CustomPojoType> create(Class<? extends FieldParser<CustomPojoType>> aClass) {
                        return new CustomPojoTypeStringParser();
                    }
                }
        );


        CsvReader csvReader = new CsvReader("C:\\Users\\Dmitrii_Kober\\Projects\\flink-starter\\flink-test-client\\src\\main\\resources\\custom_pojo_type_field.csv", executionEnvironment);
		csvReader.fieldDelimiter(",");
        csvReader.parseQuotedStrings('\'');
		DataSource<Tuple3<Integer, String, CustomPojoType>> dataSource = csvReader.types(int.class, String.class, CustomPojoType.class);
		dataSource.print();
        System.out.println();
    }
}
