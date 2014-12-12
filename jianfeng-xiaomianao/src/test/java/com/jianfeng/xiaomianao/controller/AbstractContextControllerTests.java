package com.jianfeng.xiaomianao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/resources/META-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/webmvc-config.xml" })
public class AbstractContextControllerTests extends AbstractJUnit4SpringContextTests{

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;
}
