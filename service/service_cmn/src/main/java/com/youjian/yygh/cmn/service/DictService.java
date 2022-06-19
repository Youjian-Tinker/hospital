package com.youjian.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.yygh.model.cmn.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);
}
