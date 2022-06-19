package com.youjian.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.yygh.cmn.mapper.DictMapper;
import com.youjian.yygh.cmn.service.DictService;
import com.youjian.yygh.model.cmn.Dict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl
        extends ServiceImpl<DictMapper, Dict>
        implements DictService {

    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        for (Dict dict: dictList) {
            dict.setHasChildren(isChildren(dict.getId()));
        }
        return dictList;
    }

    // 判断id下是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer integer = baseMapper.selectCount(wrapper);
        return integer != null && integer > 0;
    }
}
