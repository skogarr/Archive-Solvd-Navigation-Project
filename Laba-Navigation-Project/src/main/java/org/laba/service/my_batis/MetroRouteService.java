package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.IBusRouteDAO;
import org.laba.dao.IMetroRouteDAO;

import java.io.IOException;
import java.io.Reader;

public class MetroRouteService {
    SqlSessionFactory sqlSessionFactory;

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

    public MetroRoute save(MetroRoute metroRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.createEntity(metroRoute);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return metroRoute;
    }

    public void update(MetroRoute metroRoute) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.updateEntity(metroRoute);
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
            IMetroRouteDAO metroRouteDAO = sqlSession.getMapper(IMetroRouteDAO.class);

            try {
                metroRouteDAO.removeEntity(id);
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
