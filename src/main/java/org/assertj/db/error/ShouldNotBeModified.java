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
 * Creates an error message indicating that an assertion that verifies that a column is not modified.
 *
 * @author Régis Pouiller
 *
 */
public class ShouldNotBeModified extends BasicErrorMessageFactory {

    /**
     * Constructor.
     *
     * @param valueAtStartPoint The value at start point.
     * @param valueAtEndPoint The value at end point.
     */
    private ShouldNotBeModified(Object valueAtStartPoint, Object valueAtEndPoint) {
        super("%nExpecting :%n  <%s>%nis not modified but is :%n  <%s>", valueAtStartPoint, valueAtEndPoint);
    }

    /**
     * Creates a new <code>{@link ShouldNotBeModified}</code>.
     *
     * @param valueAtStartPoint The value at start point.
     * @param valueAtEndPoint The value at end point.
     * @return the created {@code ErrorMessageFactory}.
     */
    public static ErrorMessageFactory shouldNotBeModified(Object valueAtStartPoint, Object valueAtEndPoint) {
        return new ShouldNotBeModified(valueAtStartPoint, valueAtEndPoint);
    }
}
