package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.IBusRouteDAO;
import org.laba.dao.IBusStopDAO;

import java.io.IOException;
import java.io.Reader;

public class BusStopService {
    SqlSessionFactory sqlSessionFactory;

    public BusStopService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BusStop getById(Long id) {
        BusStop busStop;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);
            busStop = busStopDAO.getEntityById(id);
        }
        return busStop;
    }

    public BusStop save(BusStop busStop) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.createEntity(busStop);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return busStop;
    }

    public void update(BusStop busStop) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.updateEntity(busStop);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeById(long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusStopDAO busStopDAO = sqlSession.getMapper(IBusStopDAO.class);

            try {
                busStopDAO.removeEntity(id);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
