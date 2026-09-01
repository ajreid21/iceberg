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

import java.util.Map;
import org.apache.iceberg.exceptions.ValidationException;

final class TablePropertyValidation {
  private TablePropertyValidation() {}

  static void validateParquetColumnStatsProperties(Map<String, String> properties) {
    properties.forEach(TablePropertyValidation::validateParquetColumnStatsProperty);
  }

  static void validateParquetColumnStatsProperty(String key, String value) {
    if (key.startsWith(TableProperties.PARQUET_COLUMN_STATS_ENABLED_PREFIX)) {
      ValidationException.check(
          "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value),
          "Invalid value for table property %s: %s (expected true or false)",
          key,
          value);
    }
  }
}
