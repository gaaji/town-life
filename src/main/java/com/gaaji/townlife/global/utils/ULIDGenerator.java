package com.gaaji.townlife.global.utils;

import de.huxhorn.sulky.ulid.ULID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class ULIDGenerator implements IdentifierGenerator {
    private final ULID ulid = new ULID();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return ulid.nextULID();
    }
}
