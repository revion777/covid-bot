package com.revion.covidbot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "overall")
public class OverallEntity {

    @Id
    private Long id;

    private String title;

    private int sick;

    private int sickChange;

    private int healed;

    private int healedChange;

    private int died;

    private int diedChange;

}
