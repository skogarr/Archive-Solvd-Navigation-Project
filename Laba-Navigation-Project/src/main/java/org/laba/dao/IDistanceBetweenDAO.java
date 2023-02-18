package org.laba.dao;

import org.laba.model.DistanceBetween;

public interface IDistanceBetweenDAO extends IBaseDAO<DistanceBetween>{
    DistanceBetween getDistanceByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid);
}
