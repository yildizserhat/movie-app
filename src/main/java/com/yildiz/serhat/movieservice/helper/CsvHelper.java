package com.yildiz.serhat.movieservice.helper;

import com.opencsv.bean.CsvToBeanBuilder;
import com.yildiz.serhat.movieservice.domain.model.CsvAcademyAwardModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvHelper {
    private static final char CSV_COLUMN_SEPARATOR = ',';

    public static List<CsvAcademyAwardModel> convertFromFileToMovieAward(File file) {
        try {
            return new CsvToBeanBuilder<CsvAcademyAwardModel>(new FileReader(file))
                    .withSeparator(CSV_COLUMN_SEPARATOR)
                    .withIgnoreQuotations(false)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(CsvAcademyAwardModel.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
