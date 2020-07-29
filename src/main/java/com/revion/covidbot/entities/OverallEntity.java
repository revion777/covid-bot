package com.revion.covidbot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OVERALL")
public class OverallEntity {

    @Id
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "sick")
    private int sick;

    @Column(name = "sick_change")
    private int sickChange;

    @Column(name = "healed")
    private int healed;

    @Column(name = "healed_change")
    private int healedChange;

    @Column(name = "died")
    private int died;

    @Column(name = "died_change")
    private int diedChange;

}
