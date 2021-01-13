package org.example.tatrainingapi.service;

import org.example.tatrainingapi.model.User;
import org.example.tatrainingapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getByName(String filter) {
        return userRepository.findAll(new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + filter + "%");
            }
        });
    }

    public User getByLogin(String filter) {
        Optional<User> optionalUser = userRepository.findOne(new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("login"), "%" + filter + "%");
            }
        });

        return optionalUser.get();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(long id) {
        return userRepository.getOne(id);
    }
}
