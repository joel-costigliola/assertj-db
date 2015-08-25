/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
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

import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests on {@link org.assertj.db.api.assertions.AssertOnValueChronology} class :
 * {@link org.assertj.db.api.assertions.AssertOnValueChronology#isBeforeOrEqualTo(org.assertj.db.type.DateTimeValue)} method.
 *
 * @author Régis Pouiller
 *
 */
public class AssertOnValueChronology_IsBeforeOrEqualTo_String_Test extends AbstractTest {

    /**
     * This method tests the {@code isBeforeOrEqualTo} assertion method.
     */
    @Test
    @NeedReload
    public void test_is_before_or_equal_to() {
        Table table = new Table(source, "test");
        Changes changes = new Changes(table).setStartPointNow();
        update("update test set var14 = 1 where var1 = 1");
        changes.setEndPointNow();

        ChangeColumnValueAssert changeColumnValueAssert = assertThat(changes).change().column("var10")
                                                                             .valueAtEndPoint();
        ChangeColumnValueAssert changeColumnValueAssert2 = changeColumnValueAssert.isBeforeOrEqualTo("2014-05-25");
        Assertions.assertThat(changeColumnValueAssert).isSameAs(changeColumnValueAssert2);

        TableColumnValueAssert tableColumnValueAssert = assertThat(table).column("var10").value();
        TableColumnValueAssert tableColumnValueAssert2 = tableColumnValueAssert.isBeforeOrEqualTo("2014-05-25");
        Assertions.assertThat(tableColumnValueAssert).isSameAs(tableColumnValueAssert2);
    }

    /**
     * This method should fail because the value is after.
     */
    @Test
    @NeedReload
    public void should_fail_because_value_is_after() {
        Table table = new Table(source, "test");
        Changes changes = new Changes(table).setStartPointNow();
        update("update test set var14 = 1 where var1 = 1");
        changes.setEndPointNow();

        try {
            assertThat(changes).change().column("var10").valueAtEndPoint().isBeforeOrEqualTo("2014-05-24");
            fail("An exception must be raised");
        } catch (AssertionError e) {
            Assertions.assertThat(e.getMessage()).isEqualTo(String.format(
                "[Value at end point of Column at index 9 (column name : VAR10) of Change at index 0 (with primary key : [1]) of Changes on test table of 'sa/jdbc:h2:mem:test' source] %n"
                + "Expecting:%n"
                + "  <2014-05-24T09:46:30.000000000>%n"
                + "to be before or equal to %n"
                + "  <2014-05-24T00:00:00.000000000>"));
        }
        try {
            assertThat(table).column("var10").value().isBeforeOrEqualTo("2014-05-24");
            fail("An exception must be raised");
        } catch (AssertionError e) {
            Assertions.assertThat(e.getMessage()).isEqualTo(
                String.format("[Value at index 0 of Column at index 9 (column name : VAR10) of test table] %n"
                              + "Expecting:%n"
                              + "  <2014-05-24T09:46:30.000000000>%n"
                              + "to be before or equal to %n"
                              + "  <2014-05-24T00:00:00.000000000>"));
        }
    }
}
