package com.youjian.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.youjian.yygh.cmn.mapper.DictMapper;
import com.youjian.yygh.model.cmn.Dict;
import com.youjian.yygh.vo.cmn.DictEeVo;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        MapperFacade mapperFacade = new DefaultMapperFactory.Builder().build().getMapperFacade();
        Dict dict = mapperFacade.map(dictEeVo, Dict.class);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
