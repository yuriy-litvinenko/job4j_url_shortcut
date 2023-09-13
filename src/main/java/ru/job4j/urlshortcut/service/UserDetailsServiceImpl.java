package ru.job4j.urlshortcut.service;

import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@Data
public class UserDetailsServiceImpl implements UserDetailsService {
    private final DomainService domainService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = domainService.findDomainByLogin(login);
        if (user.isPresent()) {
            return new User(user.get().getLogin(), user.get().getPassword(), emptyList());
        } else {
            throw new UsernameNotFoundException(login);
        }
    }
}
