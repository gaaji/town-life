package com.gaaji.townlife.service.domain.townlife;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class Counter {
    private int value;

    public void increase() {
        if(this.value+1 > Integer.MAX_VALUE) return;
        this.value++;
    }

    public void decrease() {
        if(this.value-1 < 0) return;
        this.value--;
    }
}
