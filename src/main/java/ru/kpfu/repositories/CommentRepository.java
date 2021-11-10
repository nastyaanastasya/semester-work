package ru.kpfu.repositories;

import ru.kpfu.models.Comment;
import ru.kpfu.models.Recipe;
import ru.kpfu.models.User;
import ru.kpfu.utils.RowMapper;

import java.util.List;

public class CommentRepository {
    private DataSourceTemplate<Comment> source;
    private RowMapper<Comment> builder;

    private final String SQL_GET_BY_ID =
            "select * from public.comment where id = ?";

    private final String SQL_SAVE =
            "insert into public.comment (rating, review, user_id, recipe_id) values (?, ?, ?, ?) returning id";

    private final String SQL_DELETE =
            "delete from public.comment where id = ?";

    private final String SQL_FIND_BY_RECIPE_ID =
            "select * from public.comment where recipe_id = ? order by date desc";

    private final String SQL_FIND_USER_BY_COMMENT_ID =
            "select user_id from public.comment where id = ?";

    private final String SQL_GET_RECIPE_RATING =
            "select avg(rating) as avg from public.comment where recipe_id = ?";

    private final String SQL_GET_LIKED_USERS =
            "select user_id from public.liked_comment where comment_id = ?";

    private final String SQL_ADD_LIKED_COMMENT_USER =
            "insert into public.liked_comment (comment_id, user_id) values (?, ?)";

    private final String SQL_FIND_LIKED_COMMENTS_BY_USER_ID =
            "select * from public.liked_comment where user_id = ?";

    public CommentRepository(String driver, String url, String user, String pass) {
        builder = getCommentBuilder();
        this.source = new DataSourceTemplate<>(driver, url, user, pass);
    }

    public Comment getById(long id) {
        List<Comment> list = source.query(SQL_GET_BY_ID, builder, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public long save(Comment comment, long userId, long recipeId){
        return source.query(
                SQL_SAVE, row->row.getLong("id"), comment.getRating(),comment.getReview(), userId, recipeId
            ).get(0);
    }

    public void delete(long id){
        source.update(SQL_DELETE, id);
    }

    public List<Comment> findByRecipeId(long id){
        List<Comment> list = source.query(SQL_FIND_BY_RECIPE_ID, builder, id);
        return list.size() > 0 ? list : null;
    }

    public long findUserByCommentId(long id){
        return source.query(SQL_FIND_USER_BY_COMMENT_ID, row->row.getLong("user_id"), id).get(0);
    }

    public float findRecipeRating(long id){
        return source.query(SQL_GET_RECIPE_RATING, row->row.getFloat("avg"), id).get(0);
    }

    public List<Long> getLikedCommentUsers(long id){
        List<Long> list = source.query(SQL_GET_LIKED_USERS, row->row.getLong("user_id"), id);
        return list.size() > 0 ? list : null;
    }

    public void addLikedCommentUser(long commentId, long userId){
        source.update(SQL_ADD_LIKED_COMMENT_USER, commentId, userId);
    }

    public List<Comment> findUsersLikedComments(long id){
        List<Comment> list = source.query(SQL_FIND_LIKED_COMMENTS_BY_USER_ID, builder, id);
        return list.size() > 0 ? list : null;
    }

    private RowMapper<Comment> getCommentBuilder() {
        return row -> Comment.builder()
                .id(row.getLong("id"))
                .user(User.builder().id(row.getLong("user_id")).build())
                .recipe(Recipe.builder().id(row.getLong("recipe_id")).build())
                .rating(row.getInt("rating"))
                .review(row.getString("review"))
                .date(row.getDate("date"))
                .build();
    }
}
