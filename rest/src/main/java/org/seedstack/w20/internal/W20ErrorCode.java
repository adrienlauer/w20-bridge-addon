/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.w20.internal;


import org.seedstack.seed.ErrorCode;

enum W20ErrorCode implements ErrorCode {
    UNABLE_TO_GENERATE_MASTERPAGE,
    FRAGMENT_NOT_AVAILABLE_IN_APPLICATION,
    MISSING_REST_PREFIX_FOR_HTML5_MODE, MODULE_DOES_NOT_EXIST_IN_FRAGMENT
}
