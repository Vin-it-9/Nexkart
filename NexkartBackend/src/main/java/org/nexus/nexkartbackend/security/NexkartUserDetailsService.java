package org.nexus.nexkartbackend.security;


import org.nexus.nexkartbackend.Repository.UserRepository;
import org.nexus.nexkartbackend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NexkartUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =	userRepo.getUserByEmail(email);

        if (user != null) {
            return new NextkartUserDetails(user);

        }
        throw new UsernameNotFoundException("could not find user with email: " + email);

    }

}