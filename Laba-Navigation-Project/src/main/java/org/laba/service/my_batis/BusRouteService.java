package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.IBusRouteDAO;

import java.io.IOException;
import java.io.Reader;

public class BusRouteService {
    SqlSessionFactory sqlSessionFactory;
    IBusRouteDAO busRouteDAO;

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

    public BusRoute save(BusRoute busRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.createEntity(busRoute);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return busRoute;
    }

    public void update(BusRoute busRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.updateEntity(busRoute);
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
            IBusRouteDAO busRouteDAO = sqlSession.getMapper(IBusRouteDAO.class);

            try {
                busRouteDAO.removeEntity(id);
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
