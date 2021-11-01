package com.asswei.controller;


import cn.hutool.core.bean.BeanUtil;
import com.asswei.common.lang.Result;
import com.asswei.entity.Blog;
import com.asswei.service.BlogService;
import com.asswei.util.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author asswei
 * @since 2021-08-23
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @GetMapping("/blogs")
    public Result blogs(Integer currentPage) {
        if(currentPage == null || currentPage < 1) {currentPage = 1;}
        //分页
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return Result.succ(blog);
    }

    @GetMapping("/blog/{id}/delete")
    public Result delete(@PathVariable(name = "id") Long id){
        blogService.removeById(id);
        return Result.succ("删除成功");
    }


    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        System.out.println(blog.toString());
        Blog temp = null;
        //已有博客进行编辑
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //只能编辑用户自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        } else {
            //没有博客进行添加
            if(blog.getContent()==null || blog.getDescription()==null || blog.getTitle()==null){
                return Result.fail("缺少标题、内容或者摘要");
            }
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ("操作成功", null);
    }
}


