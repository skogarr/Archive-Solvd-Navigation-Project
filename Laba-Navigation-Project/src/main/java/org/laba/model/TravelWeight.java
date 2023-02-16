package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TravelWeight {

    private long transitPointAId;

    private long transitPointBId;

    private double carWeight;

    private double busWeight;

    private double tramWeight;

    private double metroWeight;

}
