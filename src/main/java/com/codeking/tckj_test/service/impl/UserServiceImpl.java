package com.codeking.tckj_test.service.impl;

import com.codeking.tckj_test.entity.User;
import com.codeking.tckj_test.mapper.UserMapper;
import com.codeking.tckj_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUser();
    }

    @Override
    public void createUser(User user) {
        // 设置创建时间、最后登录时间和最后更新时间
        Date now = new Date();
        user.setCreateTime(now);
        user.setLastLoginTime(now);
        user.setLastUpdateTime(now);
        // 默认设置为0
        user.setFrequency(0L);
        // salt 先随便设置一个
        user.setSalt("123");
        // 默认启用
        user.setStatus(1);

        // 检查用户名是否重复
        boolean isUsernameDuplicate = isUsernameDuplicate(user.getId(), user.getName());
        if (isUsernameDuplicate) {
            // 用户名重复，可以抛出异常或进行其他处理
            throw new RuntimeException("Username already exists.");
        }

        // 检查手机号码是否重复
        boolean isPhoneNumberDuplicate = checkPhoneNumberDuplicate(user.getId(), user.getPhoneNumber());
        if (isPhoneNumberDuplicate) {
            // 手机号码重复，可以抛出异常或进行其他处理
            throw new RuntimeException("Phone number already exists.");
        }

        // 检查邮箱格式是否正确
        if (!isValidEmail(user.getEmail())) {
            // 邮箱格式不正确，可以抛出异常或进行其他处理
            throw new RuntimeException("Invalid email format.");
        }

        // 检查邮箱是否重复
        boolean isEmailDuplicate = checkEmailDuplicate(user.getId(), user.getEmail());
        if (isEmailDuplicate) {
            // 邮箱重复，可以抛出异常或进行其他处理
            throw new RuntimeException("Email already exists.");
        }

        // 调用UserMapper的saveUser方法保存用户
        userMapper.saveUser(user);
    }


    public int updateUser(Long id, User user) {
        user.setId(id);
        user.setLastUpdateTime(new Date());
        String username = user.getName();
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();

        // 验证新的手机号码和邮箱的正则表达式
        String phoneRegex = "^1[0-9]{10}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // 判断用户名、手机号码和邮箱是否重复
        boolean isUsernameDuplicate = isUsernameDuplicate(id, username);
        boolean isPhoneNumberDuplicate = checkPhoneNumberDuplicate(id, phoneNumber);
        boolean isEmailDuplicate = checkEmailDuplicate(id, email);

        // 判断新的手机号码和邮箱是否符合正则表达式
        boolean isPhoneNumberValid = phoneNumber.matches(phoneRegex);
        boolean isEmailValid = email.matches(emailRegex);

        // 如果用户名、手机号码或邮箱重复，或者新的手机号码或邮箱不符合正则表达式，返回更新失败
        if (isUsernameDuplicate || isPhoneNumberDuplicate || isEmailDuplicate || !isPhoneNumberValid || !isEmailValid) {
            return 0;
        }
        // 执行更新操作
        userMapper.updateUser(user);
        return 1; // 更新成功
    }


    private boolean isValidEmail(String email) {
        // 简单的邮箱正则校验
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean isUsernameDuplicate(Long userId, String name) {
        // 查询数据库，检查是否存在其他用户使用相同的 name
        User existingUser = userMapper.findByName(name);

        // 判断是否存在其他用户使用相同的 name
        return existingUser != null && !existingUser.getId().equals(userId);
    }

    private boolean checkPhoneNumberDuplicate(Long userId, String phoneNumber) {
        // 查询数据库，判断是否存在其他用户使用相同的手机号码
        int count = userMapper.countByPhoneNumberExceptUserId(phoneNumber, userId);
        return count > 0;
    }


    private boolean checkEmailDuplicate(Long userId, String email) {
        int count = userMapper.countByEmailExceptUserId(email, userId);
        return count > 0;
    }


    @Override
    public List<User> getUsersByPhoneNumberSuffix(String phoneNumberSuffix) {
        return userMapper.findByPhoneNumberSuffix(phoneNumberSuffix);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public List<User> getUsersByRank(int rank) {
        return userMapper.getUsersByRank(rank);
    }

}
