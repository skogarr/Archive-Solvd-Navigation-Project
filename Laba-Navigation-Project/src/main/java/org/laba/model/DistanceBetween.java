package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DistanceBetween {

    private TransitPoint transitPointA;

    private TransitPoint transitPointB;

    private int distance;

}
