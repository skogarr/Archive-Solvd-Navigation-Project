package org.laba.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TramStopRequest {

    private long id;

    private long tramRouteId;

    private long transitPointId;

    private int stopNo;

}
