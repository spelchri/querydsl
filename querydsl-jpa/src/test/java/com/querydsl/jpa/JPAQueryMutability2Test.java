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
package com.querydsl.jpa;

import static com.querydsl.core.types.dsl.Expressions.numberOperation;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.querydsl.core.types.Operator;
import com.querydsl.jpa.domain.QCat;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.testutil.JPATestRunner;

@RunWith(JPATestRunner.class)
public class JPAQueryMutability2Test implements JPATest {

    private EntityManager entityManager;

    private final Operator customOperator = new Operator() {
        public String name() { return "custom"; }
        public String toString() { return name(); }
        public Class<?> getType() { return Object.class; }
    };

    private final JPQLTemplates customTemplates = new HQLTemplates() {{
            add(customOperator, "sign({0})");
    }};

    protected JPAQuery query() {
        return new JPAQuery(entityManager);
    }

    protected JPAQuery query(JPQLTemplates templates) {
        return new JPAQuery(entityManager, templates);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void test() {
        QCat cat = QCat.cat;
        JPAQuery query = query().from(cat);

        query.count();
        query.distinct().count();

        query.iterate(cat);
        query.iterate(cat,cat);
        query.distinct().iterate(cat);
        query.distinct().iterate(cat,cat);

        query.list(cat);
        query.list(cat,cat);
        query.distinct().list(cat);
        query.distinct().list(cat,cat);

        query.listResults(cat);
        query.distinct().listResults(cat);

        query.map(cat.name, cat);
    }

    @Test
    public void Clone() {
        QCat cat = QCat.cat;
        JPAQuery query = query().from(cat).where(cat.name.isNotNull());
        JPAQuery query2 = query.clone(entityManager);
        assertEquals(query.getMetadata().getJoins(), query2.getMetadata().getJoins());
        assertEquals(query.getMetadata().getWhere(), query2.getMetadata().getWhere());
        query2.list(cat);
    }

    @Test
    public void Clone_Custom_Templates() {
        QCat cat = QCat.cat;
        JPAQuery query = query().from(cat);
        //attach using the custom templates
        query.clone(entityManager, customTemplates)
                .uniqueResult(numberOperation(Integer.class, customOperator, cat.floatProperty));
    }

    @Test
    public void Clone_Keep_Templates() {
        QCat cat = QCat.cat;
        JPAQuery query = query(customTemplates).from(cat);
        //keep the original templates
        query.clone()
                .uniqueResult(numberOperation(Integer.class, customOperator, cat.floatProperty));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Clone_Lose_Templates() {
        QCat cat = QCat.cat;
        JPAQuery query = query(customTemplates).from(cat);
        //clone using the entitymanager's default templates
        query.clone(entityManager)
                .uniqueResult(numberOperation(Integer.class, customOperator, cat.floatProperty));
    }

}
