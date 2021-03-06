// Copyright 2013-2019 Michel Kraemer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.undercouch.gradle.tasks.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.gradle.api.file.FileCollection;
import org.junit.Test;

/**
 * Tests related to plugin outputs
 * @author Jan Berkel
 */
public class OutputsTest extends TestBaseWithMockServer {
    /**
     * Test if the outputs are empty if no sources are set
     */
    @Test
    public void emptyOutputs() {
        Download t = makeProjectAndTask();
        assertTrue(t.getOutputs().getFiles().isEmpty());
    }

    /**
     * Test if the output is generated correctly for a single source and a
     * destination file
     * @throws Exception if anything went wrong
     */
    @Test
    public void singleOutputFileWithDestinationFile() throws Exception {
        Download t = makeProjectAndTask();
        t.src(wireMockRule.baseUrl());
        File dst = folder.newFile();
        t.dest(dst);
        assertEquals(dst, t.getOutputs().getFiles().getSingleFile());
    }

    /**
     * Test if the output is generated correctly for a single source and a
     * destination folder
     */
    @Test
    public void singleOutputFileWithDestinationDirectory() {
        Download t = makeProjectAndTask();
        t.src(wireMockRule.url("test1.txt"));
        t.dest(folder.getRoot());
        assertEquals(new File(folder.getRoot(), "test1.txt"),
                t.getOutputs().getFiles().getSingleFile());
    }

    /**
     * Test if the outputs are generated correctly for multiple sources and
     * a destination folder
     */
    @Test
    public void multipleOutputFiles() {
        Download t = makeProjectAndTask();
        t.src(Arrays.asList(wireMockRule.url("test1.txt"), wireMockRule.url("test2.txt")));
        t.dest(folder.getRoot());
        final FileCollection fileCollection = t.getOutputs().getFiles();
        assertEquals(2, fileCollection.getFiles().size());
        assertTrue(fileCollection.contains(new File(folder.getRoot(), "test1.txt")));
        assertTrue(fileCollection.contains(new File(folder.getRoot(), "test2.txt")));
    }
}
