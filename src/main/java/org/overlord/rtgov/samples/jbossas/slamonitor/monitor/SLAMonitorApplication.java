/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.rtgov.samples.jbossas.slamonitor.monitor;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the REST application for the SLA
 * monitor.
 *
 */
@ApplicationScoped
@ApplicationPath("/monitor")
public class SLAMonitorApplication extends Application {

    private Set<Object> _singletons = new HashSet<Object>();
    private Set<Class<?>> _empty = new HashSet<Class<?>>();

    /**
     * This is the default constructor.
     */
    public SLAMonitorApplication() {
       _singletons.add(new SLAMonitor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Class<?>> getClasses() {
       return _empty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Object> getSingletons() {
       return _singletons;
    }
}
