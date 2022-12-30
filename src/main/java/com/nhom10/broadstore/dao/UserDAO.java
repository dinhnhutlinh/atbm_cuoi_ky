package com.nhom10.broadstore.dao;

import com.nhom10.broadstore.beans.User;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterFieldMapper(User.class)
public interface UserDAO {

    @SqlQuery("select * from user where email = :email AND password = :password AND active=1 AND role=0")
    User loginAdmin(@Bind("email") String email, @Bind("password") String password);

    @SqlQuery("select * from user where email = :email AND password = :password AND active=1 AND role=1")
    User loginCustomer(@Bind("email") String email, @Bind("password") String password);

    @SqlQuery("select id from user where email = :email AND role=0")
    String getAdminIDWithMail(@Bind("email") String email);

    @SqlQuery("select id from user where email = :email AND role=1")
    String getCustomerIDWithMail(@Bind("email") String email);

    @SqlUpdate("insert into user (`id`, `first_name`, `last_name`, `avatar`, `password`, `address`,`phone`, `email`, `create_at`, `update_at`, `active`,role) values (:id, :firstName, :lastName, :avatar, :password, :address ,:phone, :email, now(), now(), :active,1)")
    int signUpCustomer(@BindBean User user);

    @SqlQuery("SELECT * FROM user where role=0")
    List<User> listAdmin();

    @SqlQuery("SELECT * FROM user where role=1")
    List<User> listCustomer();

    @SqlUpdate("DELETE FROM user WHERE id=:id and role=0")
    int deleteAdmin(@Bind("id") String id);

    @SqlUpdate("DELETE FROM user WHERE id=:id and role=1")
    int deleteCustomer(@Bind("id") String id);

    @SqlUpdate("UPDATE  user SET active=:active where id=:id and role=0")
    int setActiveAdmin(@Bind("id") String id, @Bind("active") int active);

    @SqlUpdate("UPDATE  user SET active=:active where id=:id and role=1")
    int setActiveCustomer(@Bind("id") String id, @Bind("active") int active);

    @SqlUpdate("UPDATE user SET password=:password where id=:id and role=1")
    void changePasswordCustomer(@Bind("id") String id, @Bind("password") String password);

    @SqlUpdate("UPDATE user SET password=:password where id=:id and role=0")
    void changePasswordAdmin(@Bind("id") String id, @Bind("password") String password);

    @SqlQuery("SELECT * FROM user where id=:id and password=:password")
    User checkPassword(@Bind("id") String userId, @Bind("password") String password);

    @SqlUpdate("UPDATE user set pub_key=:pubKey where id=:id")
    void updatePublicKey(@Bind("id") String id, @Bind("pubKey") String publicKeyString);

    @SqlQuery("SELECT pub_key FROM user where id=:id")
    String getPublicKey(@Bind("id") String id);

    @SqlQuery("SELECT * FROM user where id=:id and active=1")
    User findById(@Bind("id") String parseInt);
}
