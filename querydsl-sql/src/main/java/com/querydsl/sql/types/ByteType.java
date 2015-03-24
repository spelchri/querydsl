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
package com.querydsl.sql.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * ByteType maps Byte to Byte on the JDBC level
 *
 * @author tiwe
 *
 */
public class ByteType extends AbstractNumberType<Byte> {

    public ByteType() {
        super(Types.TINYINT);
    }

    public ByteType(int type) {
        super(type);
    }

    @Override
    public Class<Byte> getReturnedClass() {
        return Byte.class;
    }

    @Override
    public Byte getValue(ResultSet rs, int startIndex) throws SQLException {
        byte val = rs.getByte(startIndex);
        return rs.wasNull() ? null : Byte.valueOf(val);
    }

    @Override
    public void setValue(PreparedStatement st, int startIndex, Byte value) throws SQLException {
        st.setByte(startIndex, value);
    }

}
