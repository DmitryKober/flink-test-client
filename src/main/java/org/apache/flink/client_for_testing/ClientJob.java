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

package org.apache.flink.client_for_testing;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.TypeExtractionUtils;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.client_for_testing.model.CustomType;
import org.apache.flink.client_for_testing.model.CustomTypeInfo;
import org.apache.flink.client_for_testing.model.CustomTypeStringParser;
import org.apache.flink.types.parser.FieldParser;
import org.apache.flink.types.parser.ParserFactory;

public class ClientJob {

	public static void main(String[] args) throws Exception {
        TypeInformation<CustomType> of = TypeInformation.of(CustomType.class);


        ParserFactory<CustomType> factory = new ParserFactory<CustomType>() {
            @Override
            public FieldParser<CustomType> create(Class<? extends FieldParser<CustomType>> parserType) {
                return new CustomTypeStringParser();
            }
        };

        Class<FieldParser<CustomType>> customTypeStringParserClass = (Class<FieldParser<CustomType>>) (Class<?>) CustomTypeStringParser.class;
        FieldParser.registerCustomParser(
                CustomType.class,
                customTypeStringParserClass,
                factory
        );

		CsvReader csvReader = new CsvReader("D:\\Projects\\flink\\flink-test-client\\src\\main\\resources\\custom_type_field.csv", ExecutionEnvironment.getExecutionEnvironment());
		csvReader.fieldDelimiter(",");
        csvReader.parseQuotedStrings('\'');
		DataSource<Tuple3<Integer, String, CustomType>> dataSource = csvReader.types(int.class, String.class, CustomType.class);
		dataSource.print();
        System.out.println();
    }
}
