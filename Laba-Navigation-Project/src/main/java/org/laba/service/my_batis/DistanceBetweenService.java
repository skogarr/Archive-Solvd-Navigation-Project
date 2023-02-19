package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IBusStopDAO;
import org.laba.dao.IDistanceBetweenDAO;
import org.laba.dao.ITravelWeightDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.DistanceBetween;
import org.laba.model.TravelWeight;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class DistanceBetweenService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(DistanceBetweenService.class.getName());

    public DistanceBetweenService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public DistanceBetween getById(Long id) {
        DistanceBetween distanceBetween;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);
            distanceBetween = distanceBetweenDAO.getEntityById(id);
        }
        return distanceBetween;
    }

    public DistanceBetween getDistanceByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid) {
        DistanceBetween distanceBetween;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);
            distanceBetween = distanceBetweenDAO.getDistanceByTransitPointAidAndTransitPointBid(transitPointAid, transitPointBid);
        }
        return distanceBetween;
    }

    public DistanceBetween save(DistanceBetween distanceBetween) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.createEntity(distanceBetween);
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
        return distanceBetween;
    }

    public void update(DistanceBetween distanceBetween) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.updateEntity(distanceBetween);
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
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.removeEntity(id);
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
