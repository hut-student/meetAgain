package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.Search;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchDAO extends BaseMapper<Search> {
}
