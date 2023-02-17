package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BusStop {

    private long id;

    private long busRouteId;

    private long transitPointId;

    private String stopNo;

}
