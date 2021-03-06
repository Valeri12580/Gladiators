package project.gladiators.service.serviceModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gladiators.model.entities.TrainingPlanWorkoutInfo;
import project.gladiators.model.entities.Workout;
import project.gladiators.model.enums.TrainingPlanType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrainingPlanServiceModel extends BaseServiceModel {

    private String name;

    private TrainingPlanType trainingPlanType;

    private List<TrainingPlanWorkoutInfoServiceModel> workouts = new ArrayList<>();
}
