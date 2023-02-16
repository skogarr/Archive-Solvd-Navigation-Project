package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DistanceBetween {

    private long transitPointAId;

    private long transitPointBId;

    private int distance;

}
