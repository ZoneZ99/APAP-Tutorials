package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.UserModel;
import apap.tutorial.gopud.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String rawPassword = encrypt(user.getPassword());
        user.setPassword(rawPassword);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    public UserModel changeUser(UserModel changedUserData) {
        UserModel existingUser = userDb.findByUsername(changedUserData.getUsername());
        existingUser.setUsername(changedUserData.getUsername());
        existingUser.setPassword(encrypt(changedUserData.getPassword()));
        return userDb.save(existingUser);
    }
}
