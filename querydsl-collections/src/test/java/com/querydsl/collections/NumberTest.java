package com.querydsl.collections;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.querydsl.core.types.path.NumberPath;

public class NumberTest {

    @Test
    public void sum() throws Exception {
        final NumberPath<BigDecimal> num = new NumberPath<BigDecimal>(BigDecimal.class, "num");
        final CollQuery query = CollQueryFactory.from(num, Arrays.asList(new BigDecimal("1.6"), new BigDecimal("1.3")));

        assertEquals(new BigDecimal("2.9"), query.select(num.sum()).uniqueResult());
    }
}
