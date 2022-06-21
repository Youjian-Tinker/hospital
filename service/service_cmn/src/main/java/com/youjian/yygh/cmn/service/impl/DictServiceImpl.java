package com.youjian.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.yygh.cmn.listener.DictListener;
import com.youjian.yygh.cmn.mapper.DictMapper;
import com.youjian.yygh.cmn.service.DictService;
import com.youjian.yygh.model.cmn.Dict;
import com.youjian.yygh.vo.cmn.DictEeVo;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class DictServiceImpl
        extends ServiceImpl<DictMapper, Dict>
        implements DictService {

    @Override
    @Cacheable(value="dict", keyGenerator = "keyGenerator")
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

    @Override
    public void export(HttpServletResponse response) {
        // 设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // 查询数据
        List<Dict> dictList = baseMapper.selectList(new QueryWrapper<>());
        // 拷贝成VO
        MapperFacade mapperFacade = new DefaultMapperFactory.Builder().build().getMapperFacade();
        List<DictEeVo> list = mapperFacade.mapAsList(dictList, DictEeVo.class);
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
