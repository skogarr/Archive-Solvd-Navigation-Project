package org.laba.dao;

import java.util.List;
import org.laba.model.TravelWeight;

public interface ITravelWeightDAO extends IBaseDAO<TravelWeight>{
    TravelWeight getTravelWeightByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid);

    List<TravelWeight> getAllTravelWeights();
}
