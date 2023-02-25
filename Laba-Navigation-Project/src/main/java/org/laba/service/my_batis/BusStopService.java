package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.*;
import org.laba.dao.IBusStopDAO;
import org.laba.exception.MapperException;
import org.laba.exception.RemoveByIdException;
import org.laba.exception.SaveException;
import org.laba.exception.UpdateException;
import org.laba.model.BusStop;
import java.io.IOException;
import java.io.Reader;

import static org.laba.exception.Error.*;
import static org.laba.exception.Error.UPDATE_ERROR;

import java.util.List;


public class BusStopService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = LogManager.getLogger(BusStopService.class.getName());

    public BusStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<BusStop> getAllByTransitPointId(Long id) {
        List<BusStop> resultList;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);
            resultList = busStopDAO.getAllStopsByTransitPointId(id);
        }
        return resultList;
    }

    public BusStop save(BusStop busStop) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.createEntity(busStop);
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
        return busStop;
    }

    public void update(BusStop busStop) throws UpdateException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.updateEntity(busStop);
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
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.removeEntity(id);
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
