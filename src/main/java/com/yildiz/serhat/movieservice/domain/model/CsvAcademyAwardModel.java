package com.yildiz.serhat.movieservice.domain.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CsvAcademyAwardModel {
    @CsvBindByName(column = "Year")
    private String year;

    @CsvBindByName(column = "Category")
    private String category;

    @CsvBindByName(column = "Nominee")
    private String nominee;

    @CsvBindByName(column = "Additional Info")
    private String additionalInfo;

    @CsvBindByName(column = "Won?")
    private String won;
}
