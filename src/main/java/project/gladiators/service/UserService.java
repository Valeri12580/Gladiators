package project.gladiators.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import project.gladiators.model.bindingModels.RoleChangeBindingModel;
import project.gladiators.model.bindingModels.UserEditBindingModel;
import project.gladiators.model.entities.User;
import project.gladiators.service.serviceModels.RoleServiceModel;
import project.gladiators.service.serviceModels.UserServiceModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface UserService  extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);


    List<UserServiceModel> getAllUsers();

    UserServiceModel findById(String id);

    void addRoleToUser(UserServiceModel userServiceModel, RoleServiceModel roleServiceModel);

    void banUser(String id);

    UserServiceModel findUserByUsername(String username);

    void addUserAnotherData(User user, String firstName, String lastName, LocalDate dateOfBirth, String gender , MultipartFile image) throws IOException;

    void editUserProfile(UserServiceModel userServiceModel);

    void changeUserPassword(UserEditBindingModel userEditBindingModel);

    void changeProfilePicture(UserServiceModel userServiceModel, MultipartFile image) throws IOException;

    void updateUser(UserServiceModel userServiceModel);

    void confirmTrainer(String username, UserServiceModel userServiceModel, MultipartFile profilePicture) throws IOException;

    void changeUserRole(String id, RoleChangeBindingModel roleChangeBindingModel);

    void saveRegisteredUser(User user);

}
