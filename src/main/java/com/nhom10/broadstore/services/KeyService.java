package com.nhom10.broadstore.services;

import com.nhom10.broadstore.beans.Slider;
import com.nhom10.broadstore.beans.UserPubKey;
import com.nhom10.broadstore.dao.SliderDAO;
import com.nhom10.broadstore.dao.UserPubKeyDAO;
import com.nhom10.broadstore.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class KeyService {
    Jdbi connect = JDBIConnector.get();
    public List<UserPubKey> getAllKeys() {
        return connect.withExtension(UserPubKeyDAO.class, handle -> handle.list());
    }
}
