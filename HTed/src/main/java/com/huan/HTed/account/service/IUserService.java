
package com.huan.HTed.account.service;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface IUserService extends IBaseService<User>, ProxySelf<IUserService> {

    User login(User user) throws UserException;

    User selectByUserName(String userName);

    void updatePassword(Long userId, String password);

    User updateUserInfo(IRequest request, User user);
}
