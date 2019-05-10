package org.jvnet.hudson.maven3.launcher;

/*
 * Copyright Olivier Lamy
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.eventspy.EventSpy;
import org.apache.maven.eventspy.internal.EventSpyDispatcher;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.execution.MavenExecutionResult;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.jvnet.hudson.maven3.listeners.HudsonMavenExecutionResult;

/**
 * @author Olivier Lamy
 * @author GEBIT Solutions GmbH
 * @since 1.9
 */
public class Maven35Launcher {

    private static HudsonMavenExecutionResult hudsonMavenExecutionResult;

    private static ExecutionListener mavenExecutionListener;

    private static List<EventSpy> eventSpiesList;

    public static ExecutionListener getMavenExecutionListener()
    {
        return mavenExecutionListener;
    }

    public static void setMavenExecutionListener( ExecutionListener listener )
    {
        mavenExecutionListener = listener;
    }

    public static List<EventSpy> getEventSpies()
    {
        return eventSpiesList;
    }

    public static void setEventSpies( List<EventSpy> theEventSpies )
    {
        eventSpiesList = theEventSpies;
    }

    public static HudsonMavenExecutionResult getMavenExecutionResult()
    {
        return hudsonMavenExecutionResult;
    }

    public static void setMavenExecutionResult( HudsonMavenExecutionResult result )
    {
        hudsonMavenExecutionResult = result;
    }

    public static int main(String[] args , ClassWorld classWorld)
            throws Exception
        {
        final MavenExecutionResult[] result = new MavenExecutionResult[1];

        File workingDirectory = new File("");
        System.setProperty(MavenCli.MULTIMODULE_PROJECT_DIRECTORY, workingDirectory.getAbsolutePath());

        ClassLoader orig = Thread.currentThread().getContextClassLoader();
        try
        {
            MavenCli mavenCli = new MavenCli(classWorld) {

                @Override
                protected void customizeContainer(PlexusContainer container) {
                    try 
                    {
                        EventSpyDispatcher eventSpyDispatcher = container.lookup(EventSpyDispatcher.class);

                        if (eventSpiesList != null && !eventSpiesList.isEmpty())
                        {
                            List<EventSpy> eventSpies = eventSpyDispatcher.getEventSpies();
                            if (eventSpies == null)
                            {
                                eventSpies = new ArrayList<EventSpy>(1);
                            }
                            eventSpies.addAll(eventSpiesList);

                            // get event spies added with plexus components
                            // see Maven31Maven addPlexusComponents
                            // PlexusModuleContributor extension
                            List<EventSpy> spies = container.lookupList(EventSpy.class);
                            if (spies != null && !spies.isEmpty())
                            {
                                eventSpies.addAll(spies);
                            }

                            eventSpies.add( new EventSpy()
                            {

                                @Override
                                public void onEvent(Object event) throws Exception
                                {
                                    if ( event instanceof MavenExecutionResult )
                                    {
                                        result[0] = (MavenExecutionResult) event;
                                    }
                                }

                                @Override
                                public void init(Context context) throws Exception
                                {
                                    // nothing
                                }

                                @Override
                                public void close() throws Exception
                                {
                                    // nothing
                                }
                            });
                            eventSpyDispatcher.setEventSpies( eventSpies );
                        }
                    } catch (ComponentLookupException ex) {
                        throw new IllegalArgumentException("Failed to setup EventSpy", ex);
                    }
                }
            };

            // we need to use this doMain to use overwritten customizeContainer
            mavenCli.doMain(args, workingDirectory.getAbsolutePath(), null, null);

            hudsonMavenExecutionResult = new HudsonMavenExecutionResult(result[0]);

            // we don't care about cli mavenExecutionResult will be study in the the plugin
            return 0;
        } catch (IllegalArgumentException ex) {
            throw new Exception(ex.getCause().getMessage(), ex.getCause());
        } finally {
            Thread.currentThread().setContextClassLoader(orig);
        }
    }
}
