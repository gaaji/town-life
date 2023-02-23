package com.gaaji.townlife.global.utils.ulid;

import de.huxhorn.sulky.ulid.ULID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ULIDGenerator implements IdentifierGenerator {
    private final ULID ulid = new ULID();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return ulid.nextULID();
    }

    public static String newULIDByRequestTime(LocalDateTime requestTime) {
        return new ULID().nextULID(requestTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}
