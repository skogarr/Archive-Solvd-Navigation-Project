package org.laba.dao;

import org.laba.model.TravelWeight;

public interface ITravelWeightDAO extends IBaseDAO<TravelWeight>{
    TravelWeight getTravelWeightByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid);
}
