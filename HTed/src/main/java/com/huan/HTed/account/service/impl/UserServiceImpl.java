
package com.huan.HTed.account.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.account.mapper.UserMapper;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.annotation.StdWho;
import com.huan.HTed.security.PasswordManager;
import com.huan.HTed.system.service.impl.BaseServiceImpl;


@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User insertSelective(IRequest request, User record) {
        if (StringUtils.isEmpty(record.getPassword())) {
            record.setPassword(passwordManager.getDefaultPassword());
        }
        record.setPasswordEncrypted(passwordManager.encode(record.getPassword()));
        userMapper.updatePasswordDate(request.getUserId());
        return super.insertSelective(request, record);
    }

    @Override
    public User login(User user) throws UserException {
        if (user == null || org.apache.commons.lang3.StringUtils.isAnyBlank(user.getUserName(), user.getPassword())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        User user1 = userMapper.selectByUserName(user.getUserName());
        if (user1 == null) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        if (User.STATUS_LOCK.equals(user1.getStatus())) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getStartActiveDate() != null && user1.getStartActiveDate().getTime() > System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getEndActiveDate() != null && user1.getEndActiveDate().getTime() < System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (!passwordManager.matches(user.getPassword(), user1.getPasswordEncrypted())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        return user1;
    }

    @Override
    public User selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    /**
     * 
     * @param request
     * @param record
     * @return
     */
    @Override
    public User updateByPrimaryKeySelective(IRequest request, @StdWho User record) {
    	userMapper.updateByPrimaryKeySelective(record);
	    return record;
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String password) {
        String passwordEncrypted = passwordManager.encode(password);
        userMapper.updatePassword(userId, passwordEncrypted);
        userMapper.updatePasswordDate(userId);
    }

    @Override
    public User updateUserInfo(IRequest request, User user) {
        userMapper.updateUserInfo(user);
        return user;
    }

}
