package com.codeking.tckj_test.service;

import com.codeking.tckj_test.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserById(Long id);

    List<User> getAllUsers();

    void createUser(User user);

    int updateUser(Long id, User user);

    List<User> getUsersByPhoneNumberSuffix(String phoneNumberSuffix);

    void deleteUser(Long id);

    User getUserByName(String keyword);

    List<User> getUsersByRank(int rank);

}
