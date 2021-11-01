package com.asswei.mapper;

import com.asswei.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author asswei
 * @since 2021-08-23
 */

public interface UserMapper extends BaseMapper<User> {
    void addUser(User user);
}
