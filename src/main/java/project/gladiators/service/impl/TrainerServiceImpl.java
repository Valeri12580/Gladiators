package project.gladiators.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gladiators.constants.ExceptionMessages;
import project.gladiators.exceptions.InvalidChangeTrainerStatusException;
import project.gladiators.model.entities.Trainer;
import project.gladiators.model.enums.Action;
import project.gladiators.repository.TrainerRepository;
import project.gladiators.service.RoleService;
import project.gladiators.service.TrainerService;
import project.gladiators.service.UserService;
import project.gladiators.service.serviceModels.RoleServiceModel;
import project.gladiators.service.serviceModels.TrainerServiceModel;
import project.gladiators.service.serviceModels.UserServiceModel;

import java.util.Objects;

@Service
public class TrainerServiceImpl implements TrainerService {
    private TrainerRepository trainerRepository;
    private UserService userService;
    private RoleService roleService;
    private ModelMapper modelMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.trainerRepository = trainerRepository;
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void changeTrainerStatus(String username, Action action) {
        UserServiceModel userServiceModel=userService.findUserByUsername(username);
        RoleServiceModel role=roleService.findByAuthority("ROLE_TRAINER");

        if(Action.valueOf("CREATE").equals(action)){
            if(userServiceModel.getAuthorities().contains(role)){
                throw new InvalidChangeTrainerStatusException(ExceptionMessages.USER_ALREADY_TRAINER);
            }
            userServiceModel.getAuthorities().add(role);

            TrainerServiceModel trainerServiceModel=new TrainerServiceModel();
            trainerServiceModel.setUserServiceModel(userServiceModel);

            Trainer trainer=this.modelMapper.map(trainerServiceModel,Trainer.class);
            trainerRepository.save(trainer);

        }else{
            if(!userServiceModel.getAuthorities().contains(role)){
                throw new InvalidChangeTrainerStatusException(ExceptionMessages.USER_NOT_TRAINER);
            }
            userServiceModel.getAuthorities().remove(role);
            trainerRepository.deleteTrainerByUser_Id(userServiceModel.getId());
        }

        userService.updateUser(userServiceModel);



    }
}
