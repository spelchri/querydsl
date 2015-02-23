package com.querydsl.collections;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.querydsl.core.types.path.NumberPath;

public class BigDecimalTest {

    @Test
    public void Arithmetic() {
        NumberPath<BigDecimal> num = new NumberPath<BigDecimal>(BigDecimal.class, "num");
        CollQuery query = CollQueryFactory.from(num, Arrays.asList(BigDecimal.ONE, BigDecimal.ONE));
        query.select(num.add(BigDecimal.ONE)).list();
        query.select(num.subtract(BigDecimal.ONE)).list();
        query.select(num.multiply(BigDecimal.ONE)).list();
        query.select(num.divide(BigDecimal.ONE)).list();
    }
    
}
