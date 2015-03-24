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

import java.lang.reflect.AnnotatedElement;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

import com.google.common.primitives.Primitives;
import com.querydsl.core.types.*;

/**
 * ArrayPath represents an array typed path
 *
 * @author tiwe
 * 
 * @param <A> array type
 * @param <E> component type
 */
public class ArrayPath<A, E> extends SimpleExpression<A> implements Path<A>, ArrayExpression<A, E> {

    private static final long serialVersionUID = 7795049264874048226L;

    private final Class<E> componentType;

    private final PathImpl<A> pathMixin;

    @Nullable
    private volatile NumberExpression<Integer> size;

    protected ArrayPath(Class<? super A> type, String variable) {
        this(type, PathMetadataFactory.forVariable(variable));
    }

    protected ArrayPath(Class<? super A> type, Path<?> parent, String property) {
        this(type, PathMetadataFactory.forProperty(parent, property));
    }
    
    @SuppressWarnings("unchecked")
    protected ArrayPath(Class<? super A> type, PathMetadata metadata) {
        super(new PathImpl<A>((Class)type, metadata));
        this.pathMixin = (PathImpl<A>)mixin;
        this.componentType = Primitives.wrap((Class<E>)type.getComponentType());
    }

    @Override
    public final <R,C> R accept(Visitor<R,C> v, C context) {
        return v.visit(pathMixin, context);
    }
    
    /**
     * Create a expression for indexed access
     *
     * @param index
     * @return
     */
    public SimplePath<E> get(Expression<Integer> index) {
        PathMetadata md = PathMetadataFactory.forArrayAccess(pathMixin, index);
        return Expressions.path(componentType, md);
    }

    /**
     * Create a expression for indexed access
     *
     * @param index
     * @return
     */
    public SimplePath<E> get(@Nonnegative int index) {
        PathMetadata md = PathMetadataFactory.forArrayAccess(pathMixin, index);
        return Expressions.path(componentType, md);
    }

    public Class<E> getElementType() {
        return componentType;
    }

    @Override
    public PathMetadata getMetadata() {
        return pathMixin.getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return pathMixin.getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return pathMixin.getAnnotatedElement();
    }

    /**
     * Create an expression for the array size
     *
     * @return
     */
    public NumberExpression<Integer> size() {
        if (size == null) {
            size = Expressions.numberOperation(Integer.class, Ops.ARRAY_SIZE, pathMixin);
        }
        return size;
    }

}
