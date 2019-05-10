package org.apache.maven.cli;

/*
 * Copyright Olivier Lamy
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


import org.apache.maven.execution.*;
import org.apache.maven.settings.building.*;
import org.codehaus.plexus.component.annotations.Component;

import java.io.PrintStream;

/**
 * Most of code is coming from asf svn repo waiting before having available
 * @author Olivier Lamy
 * @author GEBIT Solutions GmbH
 * @since 1.9
 */
@Component( role = MavenExecutionRequestBuilder.class)
public class DefaultMavenExecutionRequestBuilder
    implements MavenExecutionRequestBuilder
{
    @Override
    public MavenExecutionRequest getMavenExecutionRequest(String[] args, PrintStream printStream)
            throws MavenExecutionRequestPopulationException, SettingsBuildingException,
            MavenExecutionRequestsBuilderException {
        throw new UnsupportedOperationException();
    }
}
