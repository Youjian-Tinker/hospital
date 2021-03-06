package com.youjian.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    @ExcelProperty(value = "用户编号")
    private int uid;
    @ExcelProperty(value = "用户名称")
    private String userName;

}
