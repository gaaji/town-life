package com.gaaji.townlife.service.domain.townlife;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Counter {
    private int value;

    public static Counter create() {
        return new Counter(0);
    }

    public Counter increase() {
        if(this.value + 1 == Integer.MAX_VALUE) {
            return new Counter(this.value);
        }
        return new Counter(this.value+1);
    }

    public Counter decrease() {
        if(this.value-1 < 0) {
            return new Counter(0);
        }
        return new Counter(this.value-1);
    }
}
