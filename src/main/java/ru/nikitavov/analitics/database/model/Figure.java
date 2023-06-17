package ru.nikitavov.analitics.database.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "figure")
public class Figure implements IEntity<Integer> {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "figure_id_generator")
    @SequenceGenerator(name = "figure_id_generator", allocationSize = 1)
    @Column(name = "id", nullable = false)
    Integer id;

    Integer value;

    @ManyToOne
    Layer layer;

    @ManyToOne
    Type type;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "figure_point",
            joinColumns = {@JoinColumn(name = "figure_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "point_id", referencedColumnName = "id")}
    )
    List<Point> points;
}
