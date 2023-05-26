package com.codeking.tckj_test.mapper;

import com.codeking.tckj_test.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * User Mapper
 * </p>
 */
@Component
@Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM `orm_user` ORDER BY name ASC")
    List<User> selectAllUser();

    /**
     * 根据id查询用户
     *
     * @param id 主键id
     * @return 当前id的用户，不存在则是 {@code null}
     */
    @Select("SELECT * FROM orm_user WHERE id = #{id}")
    User selectUserById(@Param("id") Long id);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 成功 - {@code 1} 失败 - {@code 0}
     */
    @Insert("INSERT INTO orm_user (name, password, salt, email, phone_number, status, frequency, create_time, last_login_time, last_update_time) " +
            "VALUES (#{user.name}, #{user.password}, #{user.salt}, #{user.email}, #{user.phoneNumber}, #{user.status}, #{user.frequency}," +
            "#{user.createTime}, #{user.lastLoginTime}, #{user.lastUpdateTime})")
    int saveUser(@Param("user") User user);


    /**
     * 删除用户
     *
     * @param id 主键id
     * @return 成功 - {@code 1} 失败 - {@code 0}
     */
    @Delete("DELETE FROM orm_user WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 成功 - {@code 1} 失败 - {@code 0}
     */
    @Update("UPDATE orm_user SET name=#{user.name}, password=#{user.password},email=#{user.email}, " +
            "phone_number=#{user.phoneNumber},last_update_time=#{user.lastUpdateTime} WHERE id=#{user.id}")
    void updateUser(@Param("user") User user);

    @Select("SELECT * FROM orm_user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    /**
     * 判断手机号是否重复
     *
     * @param phoneNumber
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM orm_user WHERE phone_number = #{phoneNumber} AND id != #{userId}")
    int countByPhoneNumberExceptUserId(@Param("phoneNumber") String phoneNumber, @Param("userId") Long userId);

    /**
     * 判断邮箱是否重复
     *
     * @param email
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM orm_user WHERE email = #{email} AND id != #{userId}")
    int countByEmailExceptUserId(@Param("email") String email, @Param("userId") Long userId);

    /**
     * 根据用户名或手机尾号（后4位）精准查询用户
     *
     * @param phoneNumberSuffix
     * @return
     */
    @Select("SELECT * FROM orm_user WHERE phone_number LIKE CONCAT('%', #{phoneNumberSuffix})")
    List<User> findByPhoneNumberSuffix(String phoneNumberSuffix);

    @Select("SELECT * FROM orm_user ORDER BY frequency DESC LIMIT #{rank}")
    List<User> getUsersByRank(@Param("rank") int rank);

}
