package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.*;
import org.laba.dao.IMetroStopDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.MetroStop;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static org.laba.exception.Error.*;
import static org.laba.exception.Error.UPDATE_ERROR;

public class MetroStopService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = LogManager.getLogger(MetroStopService.class.getName());

    public MetroStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<MetroStop> getAllByTransitPointId(Long id) {
        List<MetroStop> resultList;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);
            resultList = metroStopDAO.getAllStopsByTransitPointId(id);
        }
        return resultList;
    }

    public MetroStop save(MetroStop metroStop) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);

            try {
                metroStopDAO.createEntity(metroStop);
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
            IMetroStopDAO metroStopDAO = sqlSession.getMapper(IMetroStopDAO.class);

            try {
                metroStopDAO.removeEntity(id);
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
