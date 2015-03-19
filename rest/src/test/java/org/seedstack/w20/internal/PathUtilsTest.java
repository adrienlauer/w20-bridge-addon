/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.w20.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathUtilsTest {
    @Test
    public void testRemoveTrailingSlash() throws Exception {
        assertThat(PathUtils.removeTrailingSlash("/path/")).isEqualTo("/path");
        assertThat(PathUtils.removeTrailingSlash("/path")).isEqualTo("/path");
        assertThat(PathUtils.removeTrailingSlash("path")).isEqualTo("path");
    }

    @Test
    public void testBuildPath() throws Exception {
        assertThat(PathUtils.buildPath("/part1", "part2", "part3", "part4")).isEqualTo("/part1/part2/part3/part4");
        assertThat(PathUtils.buildPath("/part1/", "/part2/", "/part3/", "/part4/")).isEqualTo("/part1/part2/part3/part4");
        assertThat(PathUtils.buildPath("/part1/", "/part2", "/part3", "part4/")).isEqualTo("/part1/part2/part3/part4");
        assertThat(PathUtils.buildPath("part1", "part2", "part3", "part4")).isEqualTo("part1/part2/part3/part4");
    }
}