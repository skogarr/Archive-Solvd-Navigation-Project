package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TramStop {

    private long id;

    private TramRoute tramRoute;

    private TransitPoint transitPoint;

    private int stopNo;

}
