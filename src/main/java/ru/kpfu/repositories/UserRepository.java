package ru.kpfu.repositories;

import ru.kpfu.models.User;
import ru.kpfu.utils.RowMapper;

import java.util.List;
import java.util.UUID;

public class UserRepository implements CrudRepository<User> {
    private JdbcTemplate<User> source;
    private RowMapper<User> builder;

    private final String SQL_FIND_ALL =
            "select * from public.user";

    private final String SQL_FIND_BY_EMAIL =
            "select * from public.user where email = ?";

    private final String SQL_FIND_BY_USERNAME =
            "select * from public.user where username = ?";

    private final String SQL_SAVE =
            "insert into public.user (username, email, password_hash) values (?, ?, ?) returning id";

    private final String SQL_FIND_BY_ID =
            "select * from public.user where id = ?";

    private final String SQL_UPDATE =
            "update public.user set (username, password_hash) = (?, ?) where id = ?";

    private final String SQL_DELETE =
            "delete from public.user where id = ?";

    private final String SQL_DELETE_UUID =
            "update public.user set uuid = null where id = ?";

    private final String SQL_UPDATE_UUID =
            "update public.user set uuid = ? where id = ?";

    private final String SQL_FIND_BY_UUID =
            "select * from public.user where uuid = ?";

    private final String SQL_FIND_USER_FOLLOWING =
            "select user_id from public.follower where follower_id = ?";

    private final String SQL_FIND_USER_SUBSCRIBERS =
            "select follower_id from public.follower where user_id = ?";

    private final String SQL_ADD_FOLLOWER =
            "insert into public.follower (user_id, follower_id) values (?, ?)";

    private final String SQL_UNFOLLOW =
            "delete from public.follower where user_id = ? and follower_id = ?";

    private final String SQL_FIND_IF_FOLLOW =
            "select id from public.follower where user_id = ? and follower_id = ?";

    public UserRepository(String driver, String url, String user, String pass) {
        builder = getUserBuilder();
        this.source = new JdbcTemplate<>(driver, url, user, pass);
    }

    public List<Long> findUserFollowing(long id) {
        List<Long> list = source.query(SQL_FIND_USER_FOLLOWING, row -> row.getLong("user_id"), id);
        return list.size() > 0 ? list : null;
    }

    public List<Long> findUserSubscribers(long id) {
        List<Long> list = source.query(SQL_FIND_USER_SUBSCRIBERS, row -> row.getLong("follower_id"), id);
        return list.size() > 0 ? list : null;
    }

    public void addFollower(long userId, long followerId) {
        source.update(SQL_ADD_FOLLOWER, userId, followerId);
    }

    public void unfollow(long userId, long followerId) {
        source.update(SQL_UNFOLLOW, userId, followerId);
    }

    public boolean findIfFollow(long userId, long followerId) {
        return source.query(SQL_FIND_IF_FOLLOW, row -> row.getLong("id"), userId, followerId).size() > 0;
    }

    public User findByUUID(UUID uuid) {
        List<User> users = source.query(SQL_FIND_BY_UUID, builder, uuid.toString());
        return users.size() > 0 ? users.get(0) : null;
    }

    public User findByEmail(String email) {
        List<User> users = source.query(SQL_FIND_BY_EMAIL, builder, email);
        return users.size() > 0 ? users.get(0) : null;
    }

    public User findByUsername(String username) {
        List<User> users = source.query(SQL_FIND_BY_USERNAME, builder, username);
        return users.size() > 0 ? users.get(0) : null;
    }

    public void updateUUID(UUID uuid, long id) {
        source.update(SQL_UPDATE_UUID, uuid.toString(), id);
    }

    public void removeUUID(long id) {
        source.update(SQL_DELETE_UUID, id);
    }

    @Override
    public void save(User entity) {
        long id = source.query(
                SQL_SAVE, row -> row.getLong("id"), entity.getUsername(), entity.getEmail(), entity.getPasswordHash()
        ).get(0);
        entity.setId(id);
    }

    @Override
    public User findById(long id) {
        List<User> users = source.query(SQL_FIND_BY_ID, builder, id);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public void update(User entity) {
        source.update(SQL_UPDATE, entity.getUsername(), entity.getPasswordHash(), entity.getId());
    }

    @Override
    public void delete(User entity) {
        source.update(SQL_DELETE, entity.getId());
    }

    @Override
    public List<User> findAll() {
        return source.query(SQL_FIND_ALL, builder);
    }

    private RowMapper<User> getUserBuilder() {
        return row -> User.builder()
                .id(row.getLong("id"))
                .username(row.getString("username"))
                .email(row.getString("email"))
                .passwordHash(row.getString("password_hash"))
                .build();
    }
}
