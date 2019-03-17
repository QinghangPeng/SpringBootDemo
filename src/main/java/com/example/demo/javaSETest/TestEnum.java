package com.example.demo.javaSETest;

import com.example.demo.vo.FaultMoudle;

import java.util.ArrayList;
import java.util.List;

public class TestEnum {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i=1;i<=3;i++) {
            list.add("code"+i);
        }
        for (int i=0;i<list.size();i++) {
            Integer value = FaultMoudle.getValueByCode(list.get(i));
            System.out.print(value + ",");
        }
    }
}
