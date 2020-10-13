package com.sengou.user.mapper;

import com.sengou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Update("UPDATE tb_user SET password=#{password},salt=#{salt} WHERE phone=#{phone}")
    int updateUserPasswordByPhone(User user);
}