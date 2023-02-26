package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.*;
import org.laba.dao.IMetroStopDAO;
import org.laba.dao.ITramStopDAO;
import org.laba.model.TramStop;
import org.laba.exception.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import static org.laba.exception.Error.*;
import static org.laba.exception.Error.UPDATE_ERROR;

public class TramStopService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = LogManager.getLogger(TramStopService.class.getName());

    public TramStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<TramStop> getAllByTransitPointId(Long id) {
        List<TramStop> resultList;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);
            resultList = tramStopDAO.getAllStopsByTransitPointId(id);
        }
        return resultList;
    }

    public TramStop save(TramStop tramStop) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.createEntity(tramStop);
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
        return tramStop;
    }

    public void update(TramStop tramStop) throws UpdateException, MapperException{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.updateEntity(tramStop);
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

    public void removeById(long id)  throws RemoveByIdException, MapperException{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramStopDAO tramStopDAO = sqlSession.getMapper(ITramStopDAO.class);

            try {
                tramStopDAO.removeEntity(id);
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
