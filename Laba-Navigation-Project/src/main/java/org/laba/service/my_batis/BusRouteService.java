package org.laba.service.my_batis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.*;
import org.laba.dao.IBusRouteDAO;
import org.laba.exception.*;
import org.laba.model.BusRoute;
import java.io.IOException;
import java.io.Reader;
import static org.laba.enums.Error.*;


public class BusRouteService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = Logger.getLogger(BusRouteService.class.getName());

    public BusRouteService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BusRoute getById(Long id) {
        BusRoute busRoute;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);
            busRoute = busRouteDAO.getEntityById(id);
        }
        return busRoute;
    }

    public BusRoute save(BusRoute busRoute) throws SaveException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.createEntity(busRoute);
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
        return busRoute;
    }

    public void update(BusRoute busRoute) throws UpdateException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.updateEntity(busRoute);
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
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.removeEntity(id);
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
