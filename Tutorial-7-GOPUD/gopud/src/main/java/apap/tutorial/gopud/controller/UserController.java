package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.UserModel;
import apap.tutorial.gopud.service.RoleService;
import apap.tutorial.gopud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        String rawPassword = user.getPassword();
        if (rawPassword.length() >= 8 && passwordContainsBothNumberAndLetter(rawPassword)) {
            model.addAttribute("message", "New user created successfully");
            userService.addUser(user);
        } else {
            model.addAttribute("listRole", roleService.findAll());
            model.addAttribute("message", "Password must have at least 8 characters and " +
                    "contains both alphabets and numbers ");
        }
        return "home";
    }

    private boolean passwordContainsBothNumberAndLetter(String rawPassword) {
        return rawPassword.matches(".*[a-zA-Z].*") &&
                rawPassword.matches(".*[0-9].*");
    }
}
