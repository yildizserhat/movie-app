package com.yildiz.serhat.movieservice.domain.entity;

import com.yildiz.serhat.movieservice.domain.model.CsvAcademyAwardModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "academy_award")
public class AcademyAward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nominee", columnDefinition = "text")
    private String nominee;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "category")
    private String category;

    @Column(name = "year")
    private String year;

    @Column(name = "won")
    private boolean won;

    public static AcademyAward buildAcademyAward(CsvAcademyAwardModel academyAwardModel) {
        return AcademyAward.builder()
                .nominee(academyAwardModel.getNominee())
                .additionalInfo(academyAwardModel.getAdditionalInfo())
                .category(academyAwardModel.getCategory())
                .year(academyAwardModel.getYear())
                .won(academyAwardModel.getWon().equals("YES") ? true : false)
                .build();
    }
}
