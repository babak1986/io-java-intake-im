package com.babak.iojavaintake.util;

import com.babak.iojavaintake.base.BaseDto;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CsvUtil<D extends BaseDto> {

    default List<D> loadCsvIntoEntityList(Class<D> targetClass, InputStream inputStream) {
        try {
            CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<D> rows = csvMapper.readerFor(targetClass).with(csvSchema).readValues(inputStream);
            List<D> list = new ArrayList<>();
            while (rows.hasNext()) {
                try {
                    list.add(rows.nextValue());
                } catch (Exception e) {

                }
            }
            return list;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
