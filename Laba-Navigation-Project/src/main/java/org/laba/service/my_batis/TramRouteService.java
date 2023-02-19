package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IMetroRouteDAO;
import org.laba.dao.ITramRouteDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.TramRoute;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class TramRouteService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(TramRouteService.class.getName());

    public TramRouteService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TramRoute getById(Long id) {
        TramRoute tramRoute;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);
            tramRoute = tramRouteDAO.getEntityById(id);
        }
        return tramRoute;
    }

    public TramRoute save(TramRoute tramRoute) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.createEntity(tramRoute);
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
        return tramRoute;
    }

    public void update(TramRoute tramRoute) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.updateEntity(tramRoute);
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
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.removeEntity(id);
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
