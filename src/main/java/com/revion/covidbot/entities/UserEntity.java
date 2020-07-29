package com.revion.covidbot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "lang_code")
    private String languageCode;

    @Column(name = "is_bot")
    private Boolean isBot;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "ts_create")
    private Date tsCreate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_regions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    @EqualsAndHashCode.Exclude
    private Set<RegionEntity> regions = new HashSet<>();

}
