package ru.ifmo.blog.dao.postgres;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.blog.dao.AbstractDaoFactory;
import ru.ifmo.blog.dao.UserDao;
import ru.ifmo.blog.entity.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class PostgresUserDaoTest {
    UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException {
        userDao = AbstractDaoFactory.getDaoFactory().createUserDao();
        userDao.removeAll();
    }


    @BeforeAll
    static void init() throws SQLException {
        final AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory();
//        daoFactory.connect("192.168.1.211", "test", "kirill", "KArio66540");
        daoFactory.connect("localhost", "ifmo", "ifmo", "q1w2e3");
    }

    @AfterAll
    static void afterAll() throws SQLException {
        final AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory();
        daoFactory.createUserDao().removeAll();
        daoFactory.close();
    }
    @Test
    public void shouldGetEmptyWhereNotData() throws SQLException {
        assertThat(userDao.get(1L)).isEmpty();
    }

    @Test
    public void shouldInsertAndGet() throws Exception {
        final User user = new User(null, "Name", "login", "password", LocalDateTime.now(), 23);
        userDao.save(user);

        final Optional<User> actual = userDao.get(1L);
        assertThat(actual).isNotEqualTo(Optional.of(user));
    }

}