package project.gladiators.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
public class Workout extends BaseEntity {

    @ManyToMany
    private List<Exercise> exercises;



    @Column
    private LocalDateTime registeredOn;
}
