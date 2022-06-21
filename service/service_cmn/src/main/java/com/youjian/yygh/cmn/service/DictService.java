package com.youjian.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void export(HttpServletResponse response);

    void importData(MultipartFile file);
}
