/*
 * Copyright 2011, Mysema Ltd
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
package com.querydsl.core.types.dsl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.CollectionPath;
import com.querydsl.core.types.dsl.StringPath;

public class CollectionPathTest {
    
    private CollectionPath<String,StringPath> stringPath = new CollectionPath<String,StringPath>(
            String.class, StringPath.class, 
            PathMetadataFactory.forVariable("stringPath"));
    
    @Test
    public void ToString() {        
        assertEquals("stringPath", stringPath.toString());
        assertEquals("any(stringPath)", stringPath.any().toString());
        assertEquals("eqIc(any(stringPath),X)", stringPath.any().equalsIgnoreCase("X").toString());
    }
    
    @Test
    public void GetElementType() {
        assertEquals(String.class, stringPath.getElementType());
    }
    
    @Test
    public void GetParameter() {        
        assertEquals(String.class, stringPath.getParameter(0));
    }

}
