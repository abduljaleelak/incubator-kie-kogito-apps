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
package org.kie.kogito.index.jpa.query;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.kie.kogito.index.jpa.storage.JobEntityStorage;
import org.kie.kogito.index.model.Job;
import org.kie.kogito.index.test.TestUtils;
import org.kie.kogito.index.test.query.AbstractJobQueryIT;
import org.kie.kogito.persistence.api.Storage;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kie.kogito.persistence.api.query.QueryFilterFactory.equalTo;

public abstract class AbstractJobEntityQueryIT extends AbstractJobQueryIT {

    @Inject
    JobEntityStorage storage;

    @Override
    public Storage<String, Job> getStorage() {
        return storage;
    }

    @Test
    void testCount() {
        String jobId = UUID.randomUUID().toString();
        storage.put(jobId, TestUtils
                .createJob(jobId, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(5), UUID.randomUUID().toString(),
                        RandomStringUtils.randomAlphabetic(10), "EXPECTED", 0L));
        assertThat(storage.query().count()).isNotZero();
        assertThat(storage.query().filter(List.of(equalTo("status", "UNEXPECTED"))).count()).isZero();
    }
}
