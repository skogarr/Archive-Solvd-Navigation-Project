package org.laba.service.my_batis;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.IBusStopDAO;
import org.laba.dao.IDistanceBetweenDAO;
import org.laba.dao.ITransitPointDAO;
import org.laba.dao.ITravelWeightDAO;
import org.laba.model.DistanceBetween;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;

import java.io.IOException;
import java.io.Reader;

public class DistanceBetweenService {
    SqlSessionFactory sqlSessionFactory;

    public DistanceBetweenService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public DistanceBetween getById(Long id) {
        DistanceBetween distanceBetween;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);
            distanceBetween = distanceBetweenDAO.getEntityById(id);
        }
        return distanceBetween;
    }

    public DistanceBetween getDistanceByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid) {
        DistanceBetween distanceBetween;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);
            distanceBetween = distanceBetweenDAO.getDistanceByTransitPointAidAndTransitPointBid(transitPointAid, transitPointBid);
        }
        return distanceBetween;
    }

    public DistanceBetween save(DistanceBetween distanceBetween) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.createEntity(distanceBetween);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return distanceBetween;
    }

    public void update(DistanceBetween distanceBetween) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.updateEntity(distanceBetween);
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
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);

            try {
                distanceBetweenDAO.removeEntity(id);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DistanceBetween> getAllDistances() {
        List<DistanceBetween> resulList;
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDistanceBetweenDAO distanceBetweenDAO = sqlSession.getMapper(IDistanceBetweenDAO.class);
            resulList = distanceBetweenDAO.getAllDistances();
        }
        return resulList;
    }

}
