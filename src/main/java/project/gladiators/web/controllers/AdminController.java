package project.gladiators.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.gladiators.model.bindingModels.ArticleRegisterBindingModel;
import project.gladiators.model.bindingModels.RoleChangeBindingModel;
import project.gladiators.service.ArticleService;
import project.gladiators.service.CloudinaryService;
import project.gladiators.service.RoleService;
import project.gladiators.service.UserService;
import project.gladiators.service.serviceModels.ArticleServiceModel;
import project.gladiators.service.serviceModels.RoleServiceModel;
import project.gladiators.service.serviceModels.UserServiceModel;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController  extends BaseController{
        private UserService userService;
        private RoleService roleService;
        private CloudinaryService cloudinaryService;
        private ModelMapper modelMapper;
        private ArticleService articleService;


        @Autowired
        public AdminController(UserService userService, RoleService roleService, CloudinaryService cloudinaryService, ModelMapper modelMapper, ArticleService articleService) {
                this.userService = userService;
                this.roleService = roleService;
                this.cloudinaryService = cloudinaryService;
                this.modelMapper = modelMapper;
                this.articleService = articleService;
        }


        @GetMapping("/user-management")
        public ModelAndView userRoles(){



                return super.view("/admin/user-management",new ModelAndView().
                        addObject("users",this.userService.getAllUsers()));
        }


        @PostMapping("/user-management/change")
        public ModelAndView userRoles(@RequestParam("id") String id,
                                      ModelAndView modelAndView,
                                      RoleChangeBindingModel roleChangeBindingModel){

                UserServiceModel user = this.userService
                        .findById(id);

                RoleServiceModel role = new RoleServiceModel();
                role.setAuthority(roleChangeBindingModel.getRole());



                if(role.getAuthority() != null){
                        this.userService.addRoleToUser(user, role);
                }

                modelAndView.addObject("users", this.userService
                        .getAllUsers());
                return view("/admin/user-management", modelAndView);
        }

        @GetMapping("/user-management/ban")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ModelAndView banUser(@RequestParam("id") String id,
                                    ModelAndView modelAndView){

                this.userService
                        .banUser(id);

                modelAndView.addObject("users", this.userService.getAllUsers());
                return view("/admin/all-users", modelAndView);
        }


        @GetMapping("/article-add")
        public ModelAndView addArticle(ModelAndView modelAndView){

                        modelAndView.addObject("article",new ArticleRegisterBindingModel());

                return super.view("/admin/article-add",
                       modelAndView);
        }

        @PostMapping( "/article-add")
        public ModelAndView addArticle(@Valid @ModelAttribute("article") ArticleRegisterBindingModel articleRegisterBindingModel, BindingResult result, Principal principal) throws IOException {

                if(result.hasErrors()){
                        ModelAndView modelAndView=new ModelAndView();
                        modelAndView.addObject("article",articleRegisterBindingModel);
                        modelAndView.addObject("org.springframework.validation.BindingResult.article",result);
                        return super.view("/admin/article-add",modelAndView);
                }
                String imageUrl =articleRegisterBindingModel.getImage().isEmpty()?"https://res.cloudinary.com/gladiators/image/upload/v1599061356/No-image-found_vtfx1x.jpg": cloudinaryService.uploadImage(articleRegisterBindingModel.getImage());
                ArticleServiceModel articleServiceModel = modelMapper.map(articleRegisterBindingModel, ArticleServiceModel.class);
                articleServiceModel.setImageUrl(imageUrl);

                articleService.registerArticle(articleServiceModel,principal.getName());

                return super.redirect("/admin/article-add");
        }
}