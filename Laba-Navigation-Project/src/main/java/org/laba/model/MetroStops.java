package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetroStops {

    private long metroRouteId;

    private long transitPointId;

    private int stopNo;

}
