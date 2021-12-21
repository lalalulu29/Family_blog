package ru.ifmo.blog.dao.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ru.ifmo.blog.dao.AbstractDaoFactory;
import ru.ifmo.blog.dao.UserDao;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class MysqlDaoFactory extends AbstractDaoFactory {
    private ComboPooledDataSource cpds;

    @Override
    public UserDao createUserDao() throws SQLException {
        return new MysqlUserDao(cpds.getConnection());
    }

    @Override
    public void connect(String host, String dbName, String username, String password) throws SQLException {
        try {
            cpds = new ComboPooledDataSource();

            cpds.setDriverClass("org.mariadb.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mariadb://%s/%s".formatted(host, dbName));
            cpds.setUser(username);
            cpds.setPassword(password);

            cpds.setMinPoolSize(1);
            cpds.setMaxPoolSize(5);
            cpds.setMaxStatements(180);
        } catch (PropertyVetoException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected void _close() throws SQLException {

    }
}
