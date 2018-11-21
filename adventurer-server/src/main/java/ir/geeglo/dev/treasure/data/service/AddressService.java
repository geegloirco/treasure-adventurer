package ir.geeglo.dev.treasure.data.service;

import ir.geeglo.dev.treasure.data.dao.BaseDao;
import ir.geeglo.dev.treasure.data.dao.GeegloBaseDao;
import ir.geeglo.dev.treasure.data.entity.AddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AddressService extends BaseService {
    @Autowired
    @Qualifier("GeegloBaseDao")
    private GeegloBaseDao geegloBaseDao;

    @Override
    protected BaseDao getBaseDao() {
        return geegloBaseDao;
    }

    @Override
    protected Class getEntityType() {
        return AddressEntity.class;
    }

    @Autowired
    @Qualifier("GeegloBaseDao")
    GeegloBaseDao<AddressEntity> bazarBaseDao;

    public AddressEntity selectByUserId(int userId) {
        return bazarBaseDao.findByCondition(
                AddressEntity.class, "userId", userId);
    }

    public AddressEntity findByTitle(String title) {
        AddressEntity addressEntity = bazarBaseDao
                .findByTitle(AddressEntity.class, title);
        return addressEntity;
    }
}
