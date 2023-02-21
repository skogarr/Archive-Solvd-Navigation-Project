package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IDistanceBetweenDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.DistanceBetween;

import java.io.IOException;
import java.io.Reader;

import static org.laba.exception.Error.*;
import static org.laba.exception.Error.UPDATE_ERROR;

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

    public DistanceBetween save(DistanceBetween distanceBetween) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.createEntity(distanceBetween);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.error(SAVE_ERROR.getErrorCode(), e);
                throw new SaveException(SAVE_ERROR.getDescription(), e, SAVE_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.error(MAPPER_ERROR.getErrorCode(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
        return distanceBetween;
    }

    public void update(DistanceBetween distanceBetween) throws UpdateException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.updateEntity(distanceBetween);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.error(UPDATE_ERROR.getErrorCode(), e);
                throw new UpdateException(UPDATE_ERROR.getDescription(), e, UPDATE_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.error(MAPPER_ERROR.getErrorCode(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
    }

    public void removeById(long id) throws RemoveByIdException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.removeEntity(id);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                logger.error(REMOVE_BY_ID_ERROR.getErrorCode(), e);
                throw new RemoveByIdException(REMOVE_BY_ID_ERROR.getDescription(), e, REMOVE_BY_ID_ERROR.getErrorCode());
            }

        } catch (Exception e) {
            logger.error(MAPPER_ERROR.getErrorCode(), e);
            throw new MapperException(MAPPER_ERROR.getDescription(), e, MAPPER_ERROR.getErrorCode());
        }
    }
}
