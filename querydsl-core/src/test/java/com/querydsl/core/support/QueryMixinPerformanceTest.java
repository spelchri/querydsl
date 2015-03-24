package com.querydsl.core.support;

import org.junit.Ignore;
import org.junit.Test;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;

@Ignore
public class QueryMixinPerformanceTest {

    public static final int iterations = 2000000;
    
    @Test 
    public void Normal() { // 1791
        EntityPath<DummyEntity> entity = new EntityPathBase<DummyEntity>(DummyEntity.class, "entity");
        EntityPathBase<DummyEntity> other = new EntityPathBase<DummyEntity>(DummyEntity.class, 
                PathMetadataFactory.forProperty(entity, "other"));
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            QueryMixin mixin = new QueryMixin();
            mixin.from(entity);
            mixin.where(other.eq(new DummyEntity()));
            mixin.setProjection(entity);
        }
        System.err.println(System.currentTimeMillis() - start);
    }
    
    @Test
    public void Array_Arguments() { // 2260
        EntityPath<DummyEntity> entity = new EntityPathBase<DummyEntity>(DummyEntity.class, "entity");
        EntityPath[] entities = new EntityPath[]{entity};
        EntityPathBase<DummyEntity> other = new EntityPathBase<DummyEntity>(DummyEntity.class, 
                PathMetadataFactory.forProperty(entity, "other"));
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            QueryMixin mixin = new QueryMixin();
            mixin.from(entities);
            mixin.where(other.eq(new DummyEntity()));
            mixin.setProjection(entity);
        }
        System.err.println(System.currentTimeMillis() - start);
    }
    
}
