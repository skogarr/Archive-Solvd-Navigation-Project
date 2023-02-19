package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.ITravelWeightDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.TravelWeight;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class TravelWeightService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(TravelWeightService.class.getName());

    public TravelWeightService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TravelWeight getById(Long id) {
        TravelWeight travelWeight;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            travelWeight = travelWeightDAO.getEntityById(id);
        }
        return travelWeight;
    }

    public TravelWeight getTravelWeightByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid) {
        TravelWeight travelWeight;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            travelWeight = travelWeightDAO.getTravelWeightByTransitPointAidAndTransitPointBid(transitPointAid, transitPointBid);
        }
        return travelWeight;
    }

    public TravelWeight save(TravelWeight travelWeight) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.createEntity(travelWeight);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.log(Level.ERROR, SAVE_ERROR.getDescription(), e);
                throw new SaveException(SAVE_ERROR.getDescription(), e, SAVE_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, MAPPER_ERROR.getDescription(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
        return travelWeight;
    }

    public void update(TravelWeight travelWeight) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.updateEntity(travelWeight);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.log(Level.ERROR, UPDATE_ERROR.getDescription(), e);
                throw new UpdateException(UPDATE_ERROR.getDescription(), e, UPDATE_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, MAPPER_ERROR.getDescription(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
    }

    public void removeById(long id) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.removeEntity(id);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.log(Level.ERROR, REMOVE_BY_ID_ERROR.getDescription(), e);
                throw new RemoveByIdException(REMOVE_BY_ID_ERROR.getDescription(), e, REMOVE_BY_ID_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, MAPPER_ERROR.getDescription(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
    }
}
