package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.IMetroRouteDAO;
import org.laba.dao.ITramRouteDAO;
import org.laba.model.TramRoute;

import java.io.IOException;
import java.io.Reader;

public class TramRouteService {
    SqlSessionFactory sqlSessionFactory;

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

    public TramRoute save(TramRoute tramRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.createEntity(tramRoute);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tramRoute;
    }

    public void update(TramRoute tramRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.updateEntity(tramRoute);
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
            ITramRouteDAO tramRouteDAO = sqlSession.getMapper(ITramRouteDAO.class);

            try {
                tramRouteDAO.removeEntity(id);
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
