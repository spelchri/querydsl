package com.querydsl.sql;

import static com.querydsl.core.testutil.Serialization.*;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.Arrays;

import org.junit.Test;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QTuple;
import com.querydsl.sql.domain.QSurvey;

public class RelationalPathTest {

    @Test
    public void Path() throws ClassNotFoundException, IOException {
        QSurvey survey = QSurvey.survey;
        QSurvey survey2 = serialize(survey);
        assertEquals(Arrays.asList(survey.all()), Arrays.asList(survey2.all()));
        assertEquals(survey.getMetadata(), survey2.getMetadata());
        assertEquals(survey.getMetadata(survey.id), survey2.getMetadata(survey.id));
    }

    @Test
    public void In_Tuple() throws ClassNotFoundException, IOException {
        //(survey.id, survey.name)
        QSurvey survey = QSurvey.survey;
        QTuple tuple = Projections.tuple(survey.id, survey.name);
        serialize(tuple);
        serialize(tuple.newInstance(1, "a"));
    }

}
