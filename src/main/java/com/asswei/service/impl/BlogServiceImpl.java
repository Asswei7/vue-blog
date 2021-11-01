package com.asswei.service.impl;

import com.asswei.entity.Blog;
import com.asswei.mapper.BlogMapper;
import com.asswei.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author asswei
 * @since 2021-08-23
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
