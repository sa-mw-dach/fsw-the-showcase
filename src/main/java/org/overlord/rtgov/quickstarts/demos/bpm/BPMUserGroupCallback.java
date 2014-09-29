/*
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.rtgov.quickstarts.demos.bpm;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2013 Red Hat Inc.
 */
public final class BPMUserGroupCallback extends org.switchyard.component.bpm.runtime.BPMUserGroupCallback {

    public static final Map<String,List<String>> USERS_GROUPS;
    static {
        Map<String,List<String>> ugm = new LinkedHashMap<String,List<String>>();
        ugm.put("Administrator", Arrays.asList(new String[] {"users","admin"}));
        ugm.put("kai", Arrays.asList(new String[] {"users"}));
        ugm.put("jan", Arrays.asList(new String[] {"users"}));
        USERS_GROUPS = Collections.unmodifiableMap(ugm);
    }

    private static final Properties USERS_GROUPS_PROPERTIES = new Properties();
    static {
        USERS_GROUPS_PROPERTIES.setProperty("Administrator", "admin,users");
        USERS_GROUPS_PROPERTIES.setProperty("kai", "users");
        USERS_GROUPS_PROPERTIES.setProperty("jan", "users");
    }

    public BPMUserGroupCallback() {
        super(USERS_GROUPS_PROPERTIES);
    }

}
