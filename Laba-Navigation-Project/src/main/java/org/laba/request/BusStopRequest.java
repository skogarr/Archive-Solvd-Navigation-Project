package org.laba.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BusStopRequest {
    private long id;

    @NotEmpty
    private long busRouteId;

    @NotEmpty
    private long transitPointId;

    @NotEmpty
    private String stopNo;

}
