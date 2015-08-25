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
package org.assertj.db.error;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * Creates an error message indicating that an assertion that verifies that a value is not null.
 *
 * @author Régis Pouiller
 *
 */
public class ShouldContainsOnlyNull extends BasicErrorMessageFactory {

    /**
     * Constructor.
     *
     * @param index The index of value in the failed assertion.
     */
    private ShouldContainsOnlyNull(int index) {
        super("%nExpecting to contain only null:%nbut contains not null at index: %s", index);
    }

    /**
     * Creates a new <code>{@link ShouldContainsOnlyNull}</code>.
     *
     * @param index The index of value in the failed assertion.
     * @return the created {@code ErrorMessageFactory}.
     */
    public static ErrorMessageFactory shouldContainsOnlyNull(int index) {
        return new ShouldContainsOnlyNull(index);
    }
}
