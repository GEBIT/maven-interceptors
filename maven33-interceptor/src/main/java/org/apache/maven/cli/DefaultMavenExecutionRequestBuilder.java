package org.apache.maven.cli;

/*
 * Copyright GEBIT Solutions GmbH
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.PrintStream;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulationException;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * Most of code is coming from asf svn repo waiting before having available
 * @author GEBIT Solutions GmbH
 * @since 1.8
 */
@Component( role = MavenExecutionRequestBuilder.class)
public class DefaultMavenExecutionRequestBuilder
    implements MavenExecutionRequestBuilder, Initializable
{
        @Override
        public void initialize() throws InitializationException {
            // not needded
        }

        @Override
        public MavenExecutionRequest getMavenExecutionRequest(String[] args, PrintStream printStream)
                        throws MavenExecutionRequestPopulationException, SettingsBuildingException,
                        MavenExecutionRequestsBuilderException {
                throw new UnsupportedOperationException();
        }
}
