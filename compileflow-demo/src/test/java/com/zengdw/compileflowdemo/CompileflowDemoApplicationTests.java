package com.zengdw.compileflowdemo;

import com.alibaba.compileflow.engine.ProcessEngine;
import com.alibaba.compileflow.engine.ProcessEngineFactory;
import com.alibaba.compileflow.engine.definition.tbbpm.TbbpmModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CompileflowDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testProcessEngine() {
        final String code = "bpm.leaveBpm";

        final Map<String, Object> context = new HashMap<>();
        context.put("num", "zengdw");

        final ProcessEngine<TbbpmModel> processEngine = ProcessEngineFactory.getProcessEngine();

        System.out.println(processEngine.execute(code, context));
    }
}
