package org.laba.dao;

import org.laba.model.MetroStop;

import java.util.List;

public interface IMetroStopDAO extends IBaseDAO<MetroStop>{
    List<MetroStop> getAllStopsByTransitPointId(long transitPointId);
}
