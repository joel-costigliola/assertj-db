/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.db.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

import org.assertj.db.common.AbstractTest;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.Test;

/**
 * Test on the {@code column} methods (without parameters, with int parameter and with String parameter) on value.
 * 
 * @author Régis Pouiller
 * 
 */
public class ValueAssert_Column_Test extends AbstractTest {

  /**
   * This method tests the result of {@code column} methods on values assert from a row of a table.
   */
  @Test
  public void test_with_table_and_row() {
    Table table = new Table(source, "test");

    TableAssert tableAssert = assertThat(table);
    TableColumnAssert columnAssert = tableAssert.column(1);
    TableRowValueAssert valueAssert = tableAssert.row().value(1);

    assertThat(columnAssert)
        .isSameAs(valueAssert.column(0).column())
        .isSameAs(valueAssert.column(1))
        .isSameAs(valueAssert.column("var2"));
  }

  /**
   * This method tests the result of {@code column} methods on values assert from a column of a table.
   */
  @Test
  public void test_with_table_and_column() {
    Table table = new Table(source, "test");

    TableColumnAssert columnAssert = assertThat(table).column(1);
    TableColumnValueAssert valueAssert = columnAssert.value(1);

    assertThat(columnAssert)
        .isSameAs(valueAssert.column(0).column())
        .isSameAs(valueAssert.column(1))
        .isSameAs(valueAssert.column("var2"));
  }

  /**
   * This method tests the result of {@code column} methods on values assert from a row of a request.
   */
  @Test
  public void test_with_request_and_row() {
    Request request = new Request(source, "select * from test");

    RequestAssert requestAssert = assertThat(request);
    RequestColumnAssert columnAssert = requestAssert.column(1);
    RequestRowValueAssert valueAssert = requestAssert.row().value(1);

    assertThat(columnAssert)
        .isSameAs(valueAssert.column(0).column())
        .isSameAs(valueAssert.column(1))
        .isSameAs(valueAssert.column("var2"));
  }

  /**
   * This method tests the result of {@code column} methods on values assert from a column of a request.
   */
  @Test
  public void test_with_request_and_column() {
    Request request = new Request(source, "select * from test");

    RequestColumnAssert columnAssert = assertThat(request).column(1);
    RequestColumnValueAssert valueAssert = columnAssert.value(1);

    assertThat(columnAssert)
        .isSameAs(valueAssert.column(0).column())
        .isSameAs(valueAssert.column(1))
        .isSameAs(valueAssert.column("var2"));
  }
}
