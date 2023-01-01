package com.nhom10.broadstore.dao;

import com.nhom10.broadstore.beans.OrderSignature;
import com.nhom10.broadstore.beans.Producer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(OrderSignature.class)
public interface OrderSignatureDAO {
    @SqlQuery("select * from order_signature")
    List<OrderSignature> queryAll();

    @SqlQuery("select * from order_signature where id=:id")
    OrderSignature findById(@Bind("id") int id);

    @SqlQuery("select * from order_signature where order_id=:id")
    OrderSignature findByOrderId(@Bind("id") String id);

    @SqlUpdate("insert into order_signature (id, signature, file, create_at, order_id) values (:id, :signature,:file,now(),:orderId)")
    void insert(@BindBean OrderSignature orderSignature);
}
