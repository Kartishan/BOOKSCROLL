package org.kartishan.bookheroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String gender;
    @Column(length = 1000)
    private String shortDescription;
    private String physicalDescription;
    private String education;
    private String occupation;
    @Column(length = 1000)
    private String character;
    private boolean isMainCharacter;

    private UUID bookId;

    @ManyToMany
    @JoinTable(
            name = "hero_parents",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Set<Hero> parents = new HashSet<>();
}
