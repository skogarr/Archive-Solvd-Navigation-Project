package org.laba.service.my_batis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.ITramStopDAO;
import org.laba.dao.ITransitPointDAO;
import org.laba.model.TransitPoint;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TransitPointService {
    SqlSessionFactory sqlSessionFactory;

    public TransitPointService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TransitPoint getById(Long id) {
        TransitPoint transitPoint;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITransitPointDAO transitPointDAO = sqlSession.getMapper(ITransitPointDAO.class);
            transitPoint = transitPointDAO.getEntityById(id);
        }
        return transitPoint;
    }

    public List<TransitPoint> getAllTransitPoints() {
        List<TransitPoint> resulList;
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITransitPointDAO transitPointDAO = sqlSession.getMapper(ITransitPointDAO.class);
            resulList = transitPointDAO.getAllTransitPoints();
        }
        return resulList;
    }

    public TransitPoint save(TransitPoint transitPoint) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITransitPointDAO transitPointDAO = sqlSession.getMapper(ITransitPointDAO.class);

            try {
                transitPointDAO.createEntity(transitPoint);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transitPoint;
    }

    public void update(TransitPoint transitPoint) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITransitPointDAO transitPointDAO = sqlSession.getMapper(ITransitPointDAO.class);

            try {
                transitPointDAO.updateEntity(transitPoint);
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
            ITransitPointDAO transitPointDAO = sqlSession.getMapper(ITransitPointDAO.class);

            try {
                transitPointDAO.removeEntity(id);
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
