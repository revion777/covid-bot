package com.revion.covidbot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REGIONS")
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "sick")
    private Integer sick;

    @Column(name = "sick_incr")
    private Integer sickIncr;

    @Column(name = "healed")
    private Integer healed;

    @Column(name = "healed_incr")
    private Integer healedIncr;

    @Column(name = "died")
    private Integer died;

    @Column(name = "died_incr")
    private Integer diedIncr;

}
