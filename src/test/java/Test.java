
import com.join.spring.join_spring.Autowired;
import com.join.spring.join_spring.ClassPathXmlApplicationContext;
import com.join.spring.model.User;
import com.join.spring.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;


//import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    @org.junit.Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService)classPathXmlApplicationContext.getBean("userService");
        userService.add(new User());
    }

    @org.junit.Test
    public void annotationTest() throws Exception {
        UserService userService = new UserService();
        Class clazz = UserService.class;
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            String name = field.getName();
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation!=null){
                field.setAccessible(true);
                Class<?> type = field.getType();
                try {
                    Object o = type.getDeclaredConstructor().newInstance();
                    field.set(userService,o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
        userService.add(new User());
    }
}
