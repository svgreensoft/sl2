package org.sversh.sl2.model.domain.dao;

import org.sversh.sl2.model.domain.constant.Constant;

/**
 * 
 * @author <a>Sergey Vershinin</a>
 * @since Feb 19, 2018
 *
 */
public interface ConstantDao {

    <ID, T extends Constant<ID>> T find(Class<T> constClass, ID id);

    <T extends Constant<?>> T findByStringId(Class<T> constClass, String stringId);

    <T extends Constant<?>> void save(T entity);

}
