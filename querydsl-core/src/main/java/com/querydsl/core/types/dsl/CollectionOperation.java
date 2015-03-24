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

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OperationImpl;
import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Visitor;

/**
 * @author tiwe
 *
 */
public class CollectionOperation<E> extends CollectionExpressionBase<Collection<E>, E> {

    private static final long serialVersionUID = 3154315192589335574L;

    private final Class<E> elementType;

    private final OperationImpl<Collection<E>> opMixin;

    public CollectionOperation(Class<? super E> type, Operator op, Expression<?>... args) {
        this(type, op, ImmutableList.copyOf(args));
    }

    public CollectionOperation(Class<? super E> type, Operator op, ImmutableList<Expression<?>> args) {
        super(new OperationImpl(Collection.class, op, args));
        this.opMixin = (OperationImpl)super.mixin;
        this.elementType = (Class<E>)type;
    }

    @Override
    public Class<?> getParameter(int index) {
        if (index == 0) {
            return elementType;
        } else {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }

    @Override
    @Nullable
    public <R, C> R accept(Visitor<R, C> v, C context) {
        return opMixin.accept(v, context);
    }

    @Override
    public Class<E> getElementType() {
        return elementType;
    }

}
