package org.laba.service.my_batis;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.ITransitPointDAO;
import org.laba.dao.ITravelWeightDAO;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;

import java.io.IOException;
import java.io.Reader;

public class TravelWeightService {
    SqlSessionFactory sqlSessionFactory;

    public TravelWeightService() {
        try {
            Reader reader = Resources.getResourceAsReader("myBatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TravelWeight getById(Long id) {
        TravelWeight travelWeight;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            travelWeight = travelWeightDAO.getEntityById(id);
        }
        return travelWeight;
    }

    public TravelWeight getTravelWeightByTransitPointAidAndTransitPointBid(long transitPointAid, long transitPointBid) {
        TravelWeight travelWeight;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            travelWeight = travelWeightDAO.getTravelWeightByTransitPointAidAndTransitPointBid(transitPointAid, transitPointBid);
        }
        return travelWeight;
    }

    public TravelWeight save(TravelWeight travelWeight) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.createEntity(travelWeight);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return travelWeight;
    }

    public void update(TravelWeight travelWeight) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.updateEntity(travelWeight);
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
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.removeEntity(id);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TravelWeight> getAllTravelWeights() {
        List<TravelWeight> resultList;
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            resultList = travelWeightDAO.getAllTravelWeights();
        }
        return resultList;
    }
}
