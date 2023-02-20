package org.laba.dao;

import java.util.List;
import org.laba.model.DistanceBetween;

public interface IDistanceBetweenDAO extends IBaseDAO<DistanceBetween>{
    DistanceBetween getDistanceByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid);

    List<DistanceBetween> getAllDistances();
}
