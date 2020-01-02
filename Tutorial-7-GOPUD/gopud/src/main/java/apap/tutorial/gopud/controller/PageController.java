package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.UserModel;
import apap.tutorial.gopud.service.RoleService;
import apap.tutorial.gopud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
    @PreAuthorize(value = "isAuthenticated()")
    public String changePasswordForm() {
        return "form-change-password";
    }

    @RequestMapping(value = "/user/change-password", method = RequestMethod.POST)
    @PreAuthorize(value = "isAuthenticated()")
    public String changePasswordSubmit(
            @RequestParam(value = "existingRawPassword")
                    String existingRawPassword,
            @RequestParam(value = "newPassword")
                    String newPassword,
            @RequestParam(value = "confirmNewPassword")
                    String confirmNewPassword,
            Model model) {

        UserDetails currentUser = userDetailsService.loadUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if (isOldPasswordsMatch(existingRawPassword, currentUser.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                UserModel changedUserData = new UserModel(
                        currentUser.getUsername(),
                        newPassword
                );
                userService.changeUser(changedUserData);
                model.addAttribute("changePasswordSuccess", true);
            } else {
                model.addAttribute("error", "New passwords don't match!");
            }
        } else {
            model.addAttribute("error", "Invalid old password!");
        }

        return "form-change-password";
    }

    private boolean isOldPasswordsMatch(String rawPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
