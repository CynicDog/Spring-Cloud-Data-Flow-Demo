package com.example.movieweb.movie;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class MovieFieldSetMapper implements FieldSetMapper {

    @Override
    public Object mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Movie(
                fieldSet.readString("title"),
                fieldSet.readString("actor"),
                fieldSet.readInt("year"));
    }
}
