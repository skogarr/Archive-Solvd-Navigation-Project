package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IBusRouteDAO;
import org.laba.dao.IMetroRouteDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.MetroRoute;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class MetroRouteService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(MetroRouteService.class.getName());

    public MetroRouteService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public MetroRoute getById(Long id) {
        MetroRoute metroRoute;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);
            metroRoute = metroRouteDAO.getEntityById(id);
        }
        return metroRoute;
    }

    public MetroRoute save(MetroRoute metroRoute) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.createEntity(metroRoute);
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
        return metroRoute;
    }

    public void update(MetroRoute metroRoute) throws MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.updateEntity(metroRoute);
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
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.removeEntity(id);
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
