package com.babak.iojavaintake.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
/**
 * Base class for all entity classes
 */
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    /**
     * If true, the entity is logically deleted and will not appear in search
     */
    protected Boolean deleted = false;

}
