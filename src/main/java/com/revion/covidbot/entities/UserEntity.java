package com.revion.covidbot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String langCode;

    private Boolean isBot;

    private Long chatId;

    private Date tsCreate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_regions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    @EqualsAndHashCode.Exclude
    private Set<RegionEntity> regions = new HashSet<>();

}
