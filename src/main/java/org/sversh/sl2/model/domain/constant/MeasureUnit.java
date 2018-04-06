package org.sversh.sl2.model.domain.constant;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author <a>Sergey Vershinin</a>
 * @since Feb 15, 2018
 *
 */
@Entity
@Table(name = "license_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MeasureUnit extends BaseConstant<Long> {

    public static final String MILLIGRAM = "mg";
    public static final String NUMBER = "num";


}
