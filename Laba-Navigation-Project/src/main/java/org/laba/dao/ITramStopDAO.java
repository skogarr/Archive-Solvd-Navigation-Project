package org.laba.dao;

import org.laba.model.TramStop;

import java.util.List;

public interface ITramStopDAO extends IBaseDAO<TramStop>{
    List<TramStop> getAllStopsByTransitPointId(long transitPointId);
}
