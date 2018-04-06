package org.sversh.sl2.model.domain.constant;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseConstant<PK> implements Constant<PK> {

    private PK id;
    private String name;
    private String description;

    public BaseConstant() {
        
    }
    
    public BaseConstant(PK id) {
        this.id = id;
    }
    
    @Id
    @Column(name = "id", nullable = false)
    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BaseConstant other = (BaseConstant) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
