package com.youjian.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.yygh.hosp.mapper.HospitalSetMapper;
import com.youjian.yygh.hosp.service.HospitalSetService;
import com.youjian.yygh.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl
        extends ServiceImpl<HospitalSetMapper, HospitalSet>
        implements HospitalSetService {

}
