package com.arloor.drpc.server;

import domain.employee.Employee;
import domain.employee.Skill;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Employee employee=Employee.newBuilder()
                .setId(1)
                .setName("流感胡歌那")
                .setSkills(Skill.newBuilder().setName("吃饭").build())
                .build();

        //将对象转译成字节数组,序列化
        byte[] student2ByteArray = employee.toByteArray();
        //将字节数组转译成对象,反序列化
        Employee ee = Employee.parseFrom(student2ByteArray);

        System.out.println(ee);
    }
}
