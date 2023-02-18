package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetroStop {
    private long id;

    private long metroRouteId;

    private long transitPointId;

    private int stopNo;

}
