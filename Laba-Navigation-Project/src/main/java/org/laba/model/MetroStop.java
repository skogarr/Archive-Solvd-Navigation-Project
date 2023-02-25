package org.laba.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetroStop {
    private long id;

    private MetroRoute metroRoute;

    private TransitPoint transitPoint;

    private int stopNo;

}
