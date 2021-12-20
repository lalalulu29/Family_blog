//package ru.ifmo.blog.dao.postgres;
//
//import ru.ifmo.blog.dao.PostDao;
//import ru.ifmo.blog.entity.Post;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Optional;
//
//public class PostgresPostDao implements PostDao {
//    private static final String TABLE_NAME = "posts";
//    private static final String SEQUENCE_NAME = "posts_id_sid";
//    private static final String COLUMNS = "id, author_id, created_at, title, content";
//    private static final String[] COLS = COLUMNS.split(", ");
//
//    private final Connection connection;
//
//    public PostgresPostDao(Connection connection) {
//        this.connection = connection;
//    }
//
//    @Override
//    public void save(Post post) throws SQLException {
//        try {
//            connection.setAutoCommit(false);
//            if (courseEntity.getId() == null) {
//                // insert
//                try (PreparedStatement pstmt =
//                             connection.prepareStatement(
//                                     String.format("insert into %s (%s) values(?,?,?,?)", TABLE_NAME, COLUMNS))) {
//                    final int id = nextId();
//
//                    pstmt.setInt(1, id);
//                    pstmt.setString(2, courseEntity.getTitle());
//                    pstmt.setInt(3, courseEntity.getDuration());
//                    pstmt.setDouble(4, courseEntity.getPrice());
//
//                    pstmt.executeUpdate();
//
//                    // Устанавливаем присвоенный идентификатор.
//                    courseEntity.setId(id);
//                }
//            }
//            else {
//                // maybe update
//                try (PreparedStatement pstmt = connection.prepareStatement(
//                        String.format("insert into %s (%s) values (?, ?, ?, ?) on conflict (id) do " +
//                                "update set %s = ?, %s = ?, %s = ?", TABLE_NAME, COLUMNS, COLS[1], COLS[2], COLS[3])
//                )) {
//                    pstmt.setInt(1, courseEntity.getId());
//                    pstmt.setString(2, courseEntity.getTitle());
//                    pstmt.setInt(3, courseEntity.getDuration());
//                    pstmt.setDouble(4, courseEntity.getPrice());
//                    pstmt.setString(5, courseEntity.getTitle());
//                    pstmt.setInt(6, courseEntity.getDuration());
//                    pstmt.setDouble(7, courseEntity.getPrice());
//
//                    pstmt.executeUpdate();
//                }
//            }
//
//            connection.commit();
//        }
//        catch (SQLException e) {
//            connection.rollback();
//
//            throw e;
//        }
//        finally {
//            connection.setAutoCommit(true);
//        }
//    }
//
//    @Override
//    public Optional<Post> get(Long id) throws SQLException {
//        return Optional.empty();
//    }
//
//    @Override
//    public void remove(Integer id) throws SQLException {
//
//    }
//}
