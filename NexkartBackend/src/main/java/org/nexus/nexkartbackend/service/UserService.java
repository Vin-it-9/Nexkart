package org.nexus.nexkartbackend.service;


import java.util.List;
import java.util.NoSuchElementException;

import org.nexus.nexkartbackend.Repository.RoleRepository;
import org.nexus.nexkartbackend.Repository.UserRepository;
import org.nexus.nexkartbackend.entity.Role;
import org.nexus.nexkartbackend.entity.User;
import org.nexus.nexkartbackend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {

        boolean isUpdatingUser = (user.getId() != null);

        if(isUpdatingUser) {

            User existingUser = userRepo.findById(user.getId()).get();

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
      return  userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id ,String email) {
        User userByEmail = 	userRepo.getUserByEmail(email);

        if(userByEmail == null) return true;
        boolean isCreatingNew = (id == null);

        if(isCreatingNew) {
            if(userByEmail != null ) return false ;
        }else {
            if(userByEmail.getId() != id) {
                return false;
            }
        }
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {

        try {
            return userRepo.findById(id).get();
        }
        catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find user with id "+ id);

        }

    }


    public void delete(Integer id) throws UserNotFoundException {

        Long countById = userRepo.countById(id);

        if(countById == null || countById == 0) {
            throw new UserNotFoundException("could not fint any user with ID"+id);
        }

        userRepo.deleteById(id);

    }

}