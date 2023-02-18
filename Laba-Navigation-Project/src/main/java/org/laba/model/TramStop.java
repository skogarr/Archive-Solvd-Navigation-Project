package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TramStop {

    private long id;

    private long tramRouteId;

    private long transitPointId;

    private int stopNo;

}
