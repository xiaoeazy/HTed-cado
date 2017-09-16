

package com.huan.HTed.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.mybatis.common.Mapper;

public interface UserMapper extends Mapper<User> {

    User selectByUserName(String userName);

    List<User> selectByIdList(List<Long> userIds);

    int updatePassword(@Param("userId") Long userId,@Param("password") String passwordNew);

    int updatePasswordDate(@Param("userId") Long userId);

    int updateUserInfo(User user);
}