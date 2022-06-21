package com.youjian.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {

    public static void main(String[] args) {
        String fileName = "/Users/hanguanping/Desktop/exceltest/01.xlsx";

        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userDataList.add(new UserData(i, "min"+i));
        }
        EasyExcel.write(fileName, UserData.class).sheet("用户信息").doWrite(userDataList);


    }
}
