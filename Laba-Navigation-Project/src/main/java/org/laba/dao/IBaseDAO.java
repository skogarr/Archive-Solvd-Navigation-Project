package org.laba.dao;

public interface IBaseDAO <T>{
    T getEntityById(long id);
    void updateEntity(T entity);
    void createEntity(T entity);
    void removeEntity(long id);
}
