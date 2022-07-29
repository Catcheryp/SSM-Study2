package com.yp.shirospringboot01.config;

import com.yp.shirospringboot01.pojo.User;
import com.yp.shirospringboot01.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义的 UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        // 用户名密码数据库中取
        /*String username = "root";
        String password = "123";

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        if (!userToken.getUsername().equals(username)){
            return null; // 抛出异常 UnknownAccountException
        }*/


        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());
        if (user == null){ // 没有这个用户名
            return null; // 抛出异常 UnknownAccountException
        }


        // 密码认证, shiro做; 可以加密 md5 md5盐值加密
        // return new SimpleAuthenticationInfo("", password, "");
        return new SimpleAuthenticationInfo("", user.getPass(), "");
    }
}
