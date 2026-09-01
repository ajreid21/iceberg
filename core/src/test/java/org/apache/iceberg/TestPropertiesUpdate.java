/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iceberg;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import org.apache.iceberg.exceptions.ValidationException;
import org.apache.iceberg.types.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TestPropertiesUpdate {
  @TempDir private File temp;

  @Test
  void rejectsInvalidParquetColumnStatsValue() {
    Schema schema = new Schema(Types.NestedField.optional(1, "id", Types.LongType.get()));
    Table table = TestTables.create(temp, "table", schema, PartitionSpec.unpartitioned(), 2);
    String property = TableProperties.PARQUET_COLUMN_STATS_ENABLED_PREFIX + "id";

    assertThatThrownBy(() -> table.updateProperties().set(property, "truncate(16)"))
        .isInstanceOf(ValidationException.class)
        .hasMessage(
            "Invalid value for table property %s: truncate(16) (expected true or false)", property);
  }
}
