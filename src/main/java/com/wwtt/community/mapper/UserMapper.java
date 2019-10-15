package com.wwtt.community.mapper;

import com.wwtt.community.modal.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,accountId,token,gmt_creat,gmt_modified) values(#{name},#{accountId},#{token},#{gmt_creat},#{gmt_modified})")
     void insert(User user);
}
