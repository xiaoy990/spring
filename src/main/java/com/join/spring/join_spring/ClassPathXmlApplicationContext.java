package com.join.spring.join_spring;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {

    private Map<String,Object> beans = new HashMap<String, Object>();

    public ClassPathXmlApplicationContext(String fileName) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(this.getClass().getClassLoader().getResourceAsStream(fileName));
        Element root = document.getRootElement();
        List<Element> list = root.getChildren("bean");
        for (Element e:list) {
            String id = e.getAttributeValue("id");
            String clazz = e.getAttributeValue("class");
            Object object = Class.forName(clazz).getConstructor().newInstance();
            beans.put(id,object);
            for (Element propertyElement:e.getChildren(("property"))) {
                String name = propertyElement.getAttributeValue("name");
                String bean = propertyElement.getAttributeValue("ref");
                Object beanObject = beans.get(bean);

                String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);

                Method m = object.getClass().getMethod(methodName,beanObject.getClass());
                m.invoke(object,beanObject);
            }
        }
    }

    public Object getBean(String name) {
        return beans.get(name);
    }
}
