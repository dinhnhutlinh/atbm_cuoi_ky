package com.nhom10.broadstore;

import com.nhom10.broadstore.dao.ProductDAO;
import com.nhom10.broadstore.db.JDBIConnector;
import com.nhom10.broadstore.services.ProductService;
import org.jdbi.v3.core.Jdbi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Jdbi jdbi = JDBIConnector.get();
        jdbi.useExtension(ProductDAO.class, handle -> {
            System.out.println(handle.list());
        });
    }



//        String firstname = "Linh";
//        String lastname = "Linh";
//        String email = "linhdinh69@gmail.com";
//        String phone = "0378222302";
//        String password = "linhdinh";
//        String mess= UserService.createAcc(firstname, lastname, email, phone, password);
//        System.out.println(mess);
//
//        JDBIConnector.get().useExtension(AddressDAO.class, dao->{
//            dao.insert(new Address());
//        });


}
