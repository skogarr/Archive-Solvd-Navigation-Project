package org.laba.service.my_batis;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.laba.dao.IBusStopDAO;
import org.laba.dao.IMetroStopDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.MetroStop;

import java.io.IOException;
import java.io.Reader;

import static org.laba.enums.Error.*;
import static org.laba.enums.Error.UPDATE_ERROR;

public class MetroStopService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(MetroStopService.class.getName());

    public MetroStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public MetroStop getById(Long id) {
        MetroStop metroStop;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);
            metroStop = metroStopDAO.getEntityById(id);
        }
        return metroStop;
    }

    public MetroStop save(MetroStop metroStop) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);

            try {
                metroStopDAO.createEntity(metroStop);
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
        return metroStop;
    }

    public void update(MetroStop metroStop) throws UpdateException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);

            try {
                metroStopDAO.updateEntity(metroStop);
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

    public void removeById(long id) throws RemoveByIdException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);

            try {
                metroStopDAO.removeEntity(id);
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
