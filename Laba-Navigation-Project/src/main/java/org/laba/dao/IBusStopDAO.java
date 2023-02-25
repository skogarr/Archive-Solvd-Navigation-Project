package org.laba.dao;

import org.laba.model.BusStop;

import java.util.List;

public interface IBusStopDAO extends IBaseDAO<BusStop>{
    List<BusStop> getAllStopsByTransitPointId(long transitPointId);
}
