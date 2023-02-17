package org.laba.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetroStopsRequest {
    private long id;

    private long metroRouteId;

    private long transitPointId;

    private int stopNo;

}
