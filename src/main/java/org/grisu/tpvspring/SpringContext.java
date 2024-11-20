package org.grisu.tpvspring;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContext.context == null) {
            SpringContext.context = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext no ha sido inicializado");
        }
        return context.getBean(beanClass);
    }

    public static Callback<Class<?>, Object> getBeanFactory() {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext no ha sido inicializado");
        }
        return context::getBean;
    }
}

