package org.laba.dao;

import org.laba.model.TransitPoint;

import java.util.List;

public interface ITransitPointDAO extends IBaseDAO<TransitPoint>{
    List<TransitPoint> getAllTransitPoints();
}
