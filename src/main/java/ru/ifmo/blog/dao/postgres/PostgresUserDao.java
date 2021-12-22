package ru.ifmo.blog.dao.postgres;

import ru.ifmo.blog.dao.UserDao;
import ru.ifmo.blog.entity.User;

import java.sql.*;
import java.util.Optional;

public class PostgresUserDao implements UserDao {

    private static final String TABLE_NAME = "users";
    private static final String SEQUENCE_NAME = "users_id_seq";
    private static final String COLUMNS = "id, name, login, password, created_at, age";
    private static final String[] COLS = COLUMNS.split(", ");

    private final Connection connection;

    public PostgresUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(User user) throws SQLException {
        try {
            connection.setAutoCommit(false);
            if (user.getId() == null) {
                // insert
                try (PreparedStatement pstmt =
                             connection.prepareStatement(
                                     String.format("insert into %s (%s) values(?,?,?,?,?,?)", TABLE_NAME, COLUMNS))) {
                    final long id = nextId();

                    pstmt.setLong(1, id);
                    pstmt.setString(2, user.getName());
                    pstmt.setString(3, user.getLogin());
                    pstmt.setString(4, user.getPassword());
                    pstmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
                    pstmt.setInt(6, user.getAge());



                    pstmt.executeUpdate();

                    // Устанавливаем присвоенный идентификатор.
                    user.setId(id);
                }
            }
            else {
                // maybe update
                try (PreparedStatement pstmt = connection.prepareStatement(
                        String.format("insert into %s (%s) values (?, ?, ?, ?, ?, ?) on conflict (id) do " +
                                "update set %s = ?, %s = ?, %s = ?, %s = ?, %s = ?", TABLE_NAME, COLUMNS, COLS[1], COLS[2], COLS[3], COLS[4], COLS[5])
                )) {
                    pstmt.setLong(1, user.getId());
                    pstmt.setString(2, user.getName());
                    pstmt.setString(3, user.getLogin());
                    pstmt.setString(4, user.getPassword());
                    pstmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
                    pstmt.setInt(6, user.getAge());

                    pstmt.setString(7, user.getName());
                    pstmt.setString(8, user.getLogin());
                    pstmt.setString(9, user.getPassword());
                    pstmt.setTimestamp(10, Timestamp.valueOf(user.getCreatedAt()));
                    pstmt.setInt(11, user.getAge());
//                    pstmt.set

                    pstmt.executeUpdate();
                }
            }

            connection.commit();
        }
        catch (SQLException e) {
            connection.rollback();

            throw e;
        }
        finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Optional<User> get(Long id) throws SQLException {
        try (PreparedStatement pstmt =
                     connection.prepareStatement(String.format("select %s from %s where id = ?", COLUMNS, TABLE_NAME))) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapUsers(rs));
            }
        }

        return Optional.empty();
    }

    private User mapUsers(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getTimestamp(5).toLocalDateTime(),
                rs.getInt(6)
        );
    }

    @Override
    public void remove(Integer id) throws SQLException {

    }

    @Override
    public boolean remove(Long id) throws SQLException {
        return false;
    }

    @Override
    public boolean removeAll() throws SQLException {
        try(Statement st = connection.createStatement()) {
            st.executeUpdate("delete from %s where true".formatted(TABLE_NAME));
        }
        return true;
    }

    private long nextId() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(String.format("select nextval('%s')", SEQUENCE_NAME))) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }
}
