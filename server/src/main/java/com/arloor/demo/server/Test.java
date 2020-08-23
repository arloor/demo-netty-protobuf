package com.arloor.demo.server;

import domain.employee.Employee;
import domain.employee.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        Employee employee = Employee.newBuilder()
                .setId(1)
                .setName("arloor")
                .setSkills(Skill.newBuilder().setName("吃饭").build())
                .build();

        //将对象转译成字节数组,序列化
        byte[] student2ByteArray = employee.toByteArray();
        //将字节数组转译成对象,反序列化
        Employee ee = Employee.parseFrom(student2ByteArray);

        logger.info("id: " + ee.getId());
        logger.info("名字: "+ee.getName());
        logger.info("技能：" + ee.getSkills().getName());
    }
}
