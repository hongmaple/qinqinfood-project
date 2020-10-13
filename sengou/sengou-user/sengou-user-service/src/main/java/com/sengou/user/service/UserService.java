package com.sengou.user.service;

import com.sengou.common.utils.CodecUtils;
import com.sengou.user.mapper.UserMapper;
import com.sengou.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean checkData(String data, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }
    static final String KEY_PREFIX = "user:code:phone:";

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Boolean register(User user) {

        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        // 强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());
        // 添加到数据库
        boolean b = this.userMapper.insertSelective(user) == 1;
        return b;
    }

    public User queryUser(String username, String password) {
        // 查询
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }

    public Boolean updateUserPasswordByPhone(User user){
            // 校验短信验证码
          /*  String cacheCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
            if (!StringUtils.equals(code, cacheCode)) {
                return false;
            }*/
           // 生成盐
           String salt = CodecUtils.generateSalt();
           user.setSalt(salt);

           // 对密码加密
           user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
           // 强制设置不能指定的参数为null
           user.setId(null);
           boolean b = this.userMapper.updateUserPasswordByPhone(user)==1;

           return b;
    }
}