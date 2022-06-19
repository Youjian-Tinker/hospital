package com.youjian.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youjian.yygh.common.result.Result;
import com.youjian.yygh.common.utils.MD5;
import com.youjian.yygh.hosp.service.HospitalSetService;
import com.youjian.yygh.model.hosp.HospitalSet;
import com.youjian.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    // 查询医院设置表的所有信息
    @ApiOperation("获取所有医院信息")
    @GetMapping("findAll")
    public Result findAllHospSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    // 删除医院设置
    @ApiOperation("逻辑删除医院信息")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo vo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = vo.getHosname();
        String hoscode = vo.getHoscode();
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hosname);
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hoscode);
        }
        Page<HospitalSet> pageHospSet = hospitalSetService.page(page, wrapper);
        return Result.ok(pageHospSet);

    }

    @ApiOperation("添加医院设置")
    @PostMapping("saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        // 状态1-可用， 0-不可用
        hospitalSet.setStatus(1);
        // 生成密钥
        Random random = new Random();
        String signKey = MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));
        hospitalSet.setSignKey(signKey);
        boolean save = hospitalSetService.save(hospitalSet);
        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据ID获取医院")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("医院设置锁定和解锁")
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id,
                              @PathVariable Integer status) {
        HospitalSet hospSet = hospitalSetService.getById(id);
        if (!hospSet.getStatus().equals(status)) {
            hospSet.setStatus(status);
            boolean update = hospitalSetService.updateById(hospSet);
            return update ? Result.ok() : Result.fail();
        }
        return Result.ok();
    }

    @ApiOperation("发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospSet = hospitalSetService.getById(id);
        String signKey = hospSet.getSignKey();
        String hoscode = hospSet.getHoscode();
        // todo 发送短信
        return Result.ok();
    }

}
