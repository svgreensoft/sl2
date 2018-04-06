package org.sversh.sl2.service.constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.sversh.sl2.model.domain.constant.Constant;
import org.sversh.sl2.model.domain.constant.MeasureUnit;
import org.sversh.sl2.model.domain.dao.ConstantDao;

/**
 * 
 * @author <a>Sergey Vershinin</a>
 * @since Feb 15, 2018
 *
 */
@Service
public class ConstantService implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private ConstantDao constantDao;
    


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initConstants();
    }

    private void initConstants() {
        List<Class<?>> constants = findConstantEntities();
        for (Class<?> clazz : constants) {
            List<Constant<?>> values = getValues(clazz);
            for (Constant<?> cnst : values) {
                checkConstant(cnst);
            }
        }
    }

    private List<Class<?>> findConstantEntities() {
        List<Class<?>> result = new ArrayList<Class<?>>();
        result.add(MeasureUnit.class);
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void checkConstant(Constant<?> cnst) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            //LOG.warn("No transaction");
            return;
        }
        @SuppressWarnings("unchecked")
        Class x = (Class<Constant<?>>) cnst.getClass();
        Object y = cnst.getId();
        Constant storedConst = constantDao.find(x, y);
        if (storedConst == null) {
            //LOG.info("Saving new lookup " + lookup.getClass().getName() + "[" + lookup.getId() + "]");
            constantDao.save(cnst);
        }
    }

    private List<Constant<?>> getValues(Class<?> clazz) {
        List<Constant<?>> values = new ArrayList<>();
        
        if (Constant.class.isAssignableFrom(clazz)) {
            Field[] fields = clazz.getFields();
            for (Field f : fields) { 
                if (Modifier.isStatic(f.getModifiers()) 
                        && f.getType().equals(clazz)
                        && f.getAnnotation(Deprecated.class) == null) {
                    try {
                        values.add((Constant<?>) f.get(null));
                    } catch (IllegalAccessException iae) { 
                        throw new RuntimeException(iae);
                    }
                }
            }
        }
        return values;
    }

}
