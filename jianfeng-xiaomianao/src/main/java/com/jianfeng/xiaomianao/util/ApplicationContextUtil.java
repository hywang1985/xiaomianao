package com.jianfeng.xiaomianao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);

    public static Map<String, CamelContext> map;

    private ApplicationContext applicationContext;

    private final String Cannotstop = "stop=false";

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        map = applicationContext.getBeansOfType(CamelContext.class);
    }

    public List<String> getRoutes(String contextname) {
        CamelContext camelContext = map.get(contextname);
        List<RouteDefinition> routes = ((ModelCamelContext) camelContext).getRouteDefinitions();
        List<String> routenames = new ArrayList<String>();
        for (RouteDefinition routeDefinition : routes) {
            routenames.add(routeDefinition.getId());
        }
        return routenames;
    }

    public void startRoute(String contextname, String routeid) throws Exception {
        CamelContext camelContext = map.get(contextname);
        logger.info("start context:{},route:{}", new String[] { camelContext.getName(), routeid });
        camelContext.start();
        RouteDefinition routeDefinition = ((ModelCamelContext) camelContext).getRouteDefinition(routeid);
        camelContext.startRoute(routeDefinition.getId());
        logger.info("end start context:{},route:{}", new String[] { camelContext.getName(), routeDefinition.getId() });
    }

    public void stopRoute(String contextname, String routeid) throws Exception {
        CamelContext camelContext = map.get(contextname);
        logger.info("stop context:{},route:{}", new String[] { camelContext.getName(), routeid });
        RouteDefinition routeDefinition = ((ModelCamelContext) camelContext).getRouteDefinition(routeid);
        camelContext.stopRoute(routeDefinition.getId());
        logger.info("end stop context:{},route:{}", new String[] { camelContext.getName(), routeDefinition.getId() });
    }

    public void start(String name) throws Exception {
        if (map.containsKey(name)) {
            start(map.get(name));
        }
    }

    public void start() throws Exception {
        for (CamelContext camelContext : map.values()) {
            stop(camelContext);
        }
    }

    private void start(CamelContext camelContext) throws Exception {
        logger.info("start context:{}", new String[] { camelContext.getName() });
        camelContext.start();
        for (Route route : camelContext.getRoutes()) {
            camelContext.startRoute(route.getId());
        }
        logger.info("end start context:{}", new String[] { camelContext.getName() });
    }

    public void stop() throws Exception {
        for (CamelContext camelContext : map.values()) {
            stop(camelContext);
        }
    }

    public void stop(String name) throws Exception {
        if (map.containsKey(name)) {
            stop(map.get(name));
        }
    }

    private void stop(CamelContext camelContext) throws Exception {
        logger.info("stop context:{}", new String[] { camelContext.getName() });
        for (Route route : camelContext.getRoutes()) {
            if (!route.getId().contains(Cannotstop)) {
                camelContext.stopRoute(route.getId());
            }
        }
        logger.info("end stop context:{}", new String[] { camelContext.getName() });
    }

    public Properties getMailProperties(String name) {
        Resource resource = applicationContext.getResource("classpath:META-INF/spring/" + name + ".mail.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (Exception e) {
            logger.error("load properties error: " + e.getMessage());
        }
        return properties;
    }
}
