package com.babak.iojavaintake.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseService<E extends BaseEntity> {

    protected final Logger logger;

    public BaseService() {
        logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    }

}
