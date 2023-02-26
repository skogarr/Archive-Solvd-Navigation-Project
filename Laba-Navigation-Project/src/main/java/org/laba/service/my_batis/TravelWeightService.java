package org.laba.service.my_batis;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.laba.dao.ITransitPointDAO;
import org.laba.dao.ITravelWeightDAO;
import org.laba.model.TransitPoint;
import org.apache.logging.log4j.*;
import org.laba.model.TravelWeight;
import org.laba.exception.*;
import java.io.IOException;
import java.io.Reader;
import static org.laba.exception.Error.*;
import static org.laba.exception.Error.UPDATE_ERROR;

public class TravelWeightService {
    SqlSessionFactory sqlSessionFactory;
    Logger logger = LogManager.getLogger(TravelWeightService.class.getName());


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

    public TravelWeight save(TravelWeight travelWeight)  throws SaveException, MapperException{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.createEntity(travelWeight);
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
        return travelWeight;
    }

    public void update(TravelWeight travelWeight) throws UpdateException, MapperException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.updateEntity(travelWeight);
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

    public void removeById(long id) throws RemoveByIdException, MapperException{
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);

            try {
                travelWeightDAO.removeEntity(id);
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

    public List<TravelWeight> getAllTravelWeights() {
        List<TravelWeight> resultList;
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ITravelWeightDAO travelWeightDAO = sqlSession.getMapper(ITravelWeightDAO.class);
            resultList = travelWeightDAO.getAllTravelWeights();
        }
        return resultList;
    }
}
