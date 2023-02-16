package org.laba.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BusRouteRequest {

    private long id;

    @NotEmpty
    private String routeName;

}

