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
package org.assertj.db.api.origin;

import org.assertj.db.api.navigation.ToChange;
import org.assertj.db.api.navigation.ToChanges;

/**
 * Defines a class which is the {@link org.assertj.db.api.origin.Origin} of another
 * and have {@link org.assertj.db.type.Changes}.
 *
 * @author Régis Pouiller
 */
public interface OriginWithChanges
    extends Origin,
    ToChanges,
    ToChange {
}
