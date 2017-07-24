/*
 */
package br.com.hermes.model.base;

import java.util.Objects;

/**
 * Classe abstrata utilizada para definir os atribuidos comuns das classes de entidade do sistema.
 * 
 * @author Diego Dulval
 * @version 1.0
 * @since Release 01
 */
public abstract class BaseEntity {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

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
        final BaseEntity other = (BaseEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseEntity{" + "id=" + id + '}';
    }
    
    
}
