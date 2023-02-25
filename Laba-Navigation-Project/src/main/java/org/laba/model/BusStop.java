package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BusStop {

    private long id;

    private BusRoute busRoute;

    private TransitPoint transitPoint;

    private String stopNo;

}
