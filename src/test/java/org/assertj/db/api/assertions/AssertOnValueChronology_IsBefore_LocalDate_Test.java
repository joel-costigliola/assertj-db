/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2015-2020 the original author or authors.
 */
package org.assertj.db.api.assertions;

import org.assertj.core.api.Assertions;
import org.assertj.db.api.ChangeColumnValueAssert;
import org.assertj.db.api.TableColumnValueAssert;
import org.assertj.db.common.AbstractTest;
import org.assertj.db.common.NeedReload;
import org.assertj.db.type.Changes;
import org.assertj.db.type.Table;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests on {@link org.assertj.db.api.assertions.AssertOnValueChronology} class :
 * {@link org.assertj.db.api.assertions.AssertOnValueChronology#isBefore(java.time.LocalDate)} method.
 *
 * @author Yosuke Nishikawa
 */
public class AssertOnValueChronology_IsBefore_LocalDate_Test extends AbstractTest {

  /**
   * This method tests the {@code isBefore} assertion method.
   */
  @Test
  @NeedReload
  public void test_is_before() {
    Table table = new Table(source, "test");
    Changes changes = new Changes(table).setStartPointNow();
    update("update test set var14 = 1 where var1 = 1");
    changes.setEndPointNow();

    ChangeColumnValueAssert changeColumnValueAssert = assertThat(changes).change().column("var10").valueAtEndPoint();
    ChangeColumnValueAssert changeColumnValueAssert2 = changeColumnValueAssert.isBefore(LocalDate.of(2014, 5, 25));
    Assertions.assertThat(changeColumnValueAssert).isSameAs(changeColumnValueAssert2);

    TableColumnValueAssert tableColumnValueAssert = assertThat(table).column("var10").value();
    TableColumnValueAssert tableColumnValueAssert2 = tableColumnValueAssert.isBefore(LocalDate.of(2014, 5, 25));
    Assertions.assertThat(tableColumnValueAssert).isSameAs(tableColumnValueAssert2);
  }

  /**
   * This method should fail because the value is after or equal to.
   */
  @Test
  @NeedReload
  public void should_fail_because_value_is_after_or_equal_to() {
    Table table = new Table(source, "test");
    Changes changes = new Changes(table).setStartPointNow();
    update("update test set var14 = 1 where var1 = 1");
    changes.setEndPointNow();

    try {
      assertThat(changes).change().column("var10").valueAtEndPoint().isBefore(LocalDate.of(2014, 5, 24));
      fail("An exception must be raised");
    } catch (AssertionError e) {
      Assertions.assertThat(e.getMessage()).isEqualTo(String.format("[Value at end point of Column at index 9 (column name : VAR10) of Change at index 0 (with primary key : [1]) of Changes on TEST table of 'sa/jdbc:h2:mem:test' source] %n"
                                                      + "Expecting:%n"
                                                      + "  <2014-05-24T09:46:30.000000000>%n"
                                                      + "to be before %n"
                                                      + "  <2014-05-24T00:00:00.000000000>"));
    }
    try {
      assertThat(table).column("var10").value().isBefore(LocalDate.of(2014, 5, 24));
      fail("An exception must be raised");
    } catch (AssertionError e) {
      Assertions.assertThat(e.getMessage()).isEqualTo(String.format("[Value at index 0 of Column at index 9 (column name : VAR10) of TEST table] %n"
                                                      + "Expecting:%n"
                                                      + "  <2014-05-24T09:46:30.000000000>%n"
                                                      + "to be before %n"
                                                      + "  <2014-05-24T00:00:00.000000000>"));
    }
  }
}
