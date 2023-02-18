package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TravelWeight {

    private TransitPoint transitPointA;

    private TransitPoint transitPointB;

    private double carWeight;

    private double busWeight;

    private double tramWeight;

    private double metroWeight;

}
