package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IMetroStopDAO;
import org.laba.dao.ITramStopDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.TramStop;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class TramStopService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(TramStopService.class.getName());

    public TramStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TramStop getById(Long id) {
        TramStop tramStop;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);
            tramStop = tramStopDAO.getEntityById(id);
        }
        return tramStop;
    }

    public TramStop save(TramStop tramStop) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.createEntity(tramStop);
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
        return tramStop;
    }

    public void update(TramStop tramStop) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.updateEntity(tramStop);
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
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.removeEntity(id);
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
