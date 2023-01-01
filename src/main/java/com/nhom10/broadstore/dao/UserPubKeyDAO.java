package com.nhom10.broadstore.dao;

import com.nhom10.broadstore.beans.UserPubKey;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import javax.ws.rs.BeanParam;
import java.util.List;

@RegisterBeanMapper(UserPubKey.class)
public interface UserPubKeyDAO {

    @SqlQuery(" select * from user_pub_key where id=:id")
    UserPubKey findById(@Bind("id") int id);

    @SqlQuery(" select * from user_pub_key")
    List<UserPubKey> list();

    @SqlUpdate("insert into user_pub_key (pub_key, status, create_at, user_id)values (:pubKey, :status,now(), :userId)")
    void insert(@BindBean UserPubKey userPubKey);

    @SqlUpdate("update user_pub_key set status=:status where id=:id")
    void updateStatus(@Bind("id") int id, @Bind("status") int status);

    @SqlUpdate("update user_pub_key set  status = 1 where user_id=:userId")
    void setKeyOld(@Bind("userId") String userId);
}
