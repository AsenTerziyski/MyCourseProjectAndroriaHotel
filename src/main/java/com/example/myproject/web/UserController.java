package com.example.myproject.web;

import com.example.myproject.model.binding.UserDeleteBindingModel;
import com.example.myproject.model.binding.UserRegistrationBindingModel;
import com.example.myproject.model.entities.UserBrowser;
import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.service.UserBrowserService;
import com.example.myproject.service.UserService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import com.example.myproject.web.exceptions.UsernameNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Controller
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserBrowserService userBrowserService;

    public UserController(ModelMapper modelMapper, UserService userService, UserBrowserService userBrowserService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userBrowserService = userBrowserService;
    }

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String loginFailure(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes attributes) {
        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", userName);
        return "redirect:/users/login";
    }

    @GetMapping("/user/register")
    public String getRegisterPage(Model model) {
        if (!model.containsAttribute("occupiedUsername")) {
            model.addAttribute("occupiedUsername", false);
        }
        return "user-add";
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @PostMapping("/user/add-new-user")
    public String registerPost(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegistrationBindingModel.getPassword().equals(userRegistrationBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);
            return "user-add";
        }

        UserRegistrationServiceModel userRegistrationServiceModel = this.modelMapper.map(userRegistrationBindingModel, UserRegistrationServiceModel.class);

        boolean usernameExists = this.userService.checkIfUsernameExists(userRegistrationBindingModel.getUsername());

        if (usernameExists) {
            redirectAttributes
                    .addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel)
                    .addFlashAttribute("occupiedUsername", true);
            return "redirect:/user/register";
        }

        this.userService.registerNewUser(userRegistrationServiceModel);
        return "index_androria";
    }

    @GetMapping("/user/delete-user")
    private String getDeleteUserPage() {
        return "user-delete";
    }

    @ModelAttribute
    public UserDeleteBindingModel userDeleteBindingModel() {
        return new UserDeleteBindingModel();
    }

    @DeleteMapping("/user/delete-user")
    public String deleteUser(@Valid UserDeleteBindingModel userDeleteBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userDeleteBindingModel", userDeleteBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDeleteBindingModel",
                    bindingResult);
            return "user-delete";
        }

        if (!this.userService.principalIsAdmin(principal)) {
            throw new UserNotSupportedOperation(principal.getName());
        }

        String username = userDeleteBindingModel.getUsername();
        UserEntity userForDeleting = this.userService.findUserByUsername(username);

        if (userForDeleting == null) {
            throw new UsernameNotFoundException(username);
        }

        UserRoleEntity userRoleEntity = userForDeleting.getRoles().stream().filter(r -> r.getRole().name().equalsIgnoreCase("admin")).findAny().orElse(null);

        if (userRoleEntity != null) {
            throw new UserNotSupportedOperation(userForDeleting.getUsername());
        }
        boolean deleted = this.userService.delete(userDeleteBindingModel.getUsername());
        if (deleted) {
            return "userDeletedConfirmation";
        }
        return "user-delete";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleDbExceptions(UsernameNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("userNotFound");
        modelAndView.addObject("userName", e.getUsername());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(UserNotSupportedOperation.class)
    public ModelAndView handleDbExceptions(UserNotSupportedOperation e) {
        ModelAndView modelAndView = new ModelAndView("userNotSupportedOperation");
        modelAndView.addObject("userName", e.getUsername());
        modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
        return modelAndView;
    }

    @GetMapping("/users-browser")
    private String getUsersWhoBrowsedToday(Model model) {
        LocalDate now = LocalDate.now();
        List<UserBrowser> userBrowsers = this.userBrowserService.usersWhoBrowsedOn(now);
        model.addAttribute("usersWhoBrowsedToday", userBrowsers);
        return "user-browser";
    }

}
