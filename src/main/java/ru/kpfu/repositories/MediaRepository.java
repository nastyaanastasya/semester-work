package ru.kpfu.repositories;

import ru.kpfu.models.MediaFile;
import ru.kpfu.utils.RowMapper;

import java.util.List;

public class MediaRepository {
    private JdbcTemplate<MediaFile> source;
    private RowMapper<MediaFile> builder;

    private final String SQL_FIND_BY_ID = "select * from public.media where id = ?";
    private final String SQL_FIND_BY_NAME = "select * from public.media where filename = ?";

    private final String SQL_UPDATE_USER_MEDIA = "update public.user set profile_img_id = ? where id = ?";
    private final String SQL_SAVE_RECIPE_MEDIA = "insert into recipe_media (recipe_id, media_id) values (?, ?)";

    private final String SQL_UPDATE = "update public.media set (filename, mime_type) values (?, ?) where id = ?";
    private final String SQL_SAVE = "insert into public.media (filename, mime_type) values (?, ?) returning id";
    private final String SQL_DELETE = "delete from public.media where id = ?";

    private final String SQL_FIND_BY_RECIPE_ID = "select m.id, m.filename, m.mime_type " +
                                                    "from public.media as m join public.recipe_media as rm " +
                                                    "on m.id = rm.media_id " +
                                                    "where rm.recipe_id = ?";

    private final String SQL_FIND_BY_USER_ID = "select m.id, m.filename, m.mime_type " +
                                                "from public.media as m join public.user as u " +
                                                "on m.id = u.profile_img_id " +
                                                "where u.id = ?";

    public MediaRepository(String driver, String url, String user, String pass) {
        builder = getMediaBuilder();
        this.source = new JdbcTemplate<>(driver, url, user, pass);
    }

    public void saveUserMedia(long userId, long mediaId) {
        source.update(SQL_UPDATE_USER_MEDIA, mediaId, userId);
    }

    public void saveRecipeMedia(long recipeId, long mediaId) {
        source.update(SQL_SAVE_RECIPE_MEDIA, recipeId, mediaId);
    }

    public long save(MediaFile file) {
        return source.query(SQL_SAVE, row->row.getLong("id"), file.getName(), file.getMimeType()).get(0);
    }

    public void update(MediaFile file) {
        source.update(SQL_UPDATE, file.getName(), file.getMimeType(), file.getId());
    }

    public void deleteById(long id) {
        source.update(SQL_DELETE, id);
    }

    public MediaFile findByName(String name) {
        List<MediaFile> media = source.query(SQL_FIND_BY_NAME, builder, name);
        return media.size() > 0 ? media.get(0) : null;
    }

    public MediaFile findByUserId(long id) {
        List<MediaFile> media = source.query(SQL_FIND_BY_USER_ID, builder, id);
        return media.size() > 0 ? media.get(0) : null;
    }

    public MediaFile findById(long id) {
        List<MediaFile> media = source.query(SQL_FIND_BY_ID, builder, id);
        return media.size() > 0 ? media.get(0) : null;
    }

    public List<MediaFile> findByRecipeId(long id) {
        List<MediaFile> media = source.query(SQL_FIND_BY_RECIPE_ID, builder, id);
        return media.size() > 0 ? media : null;
    }

    private RowMapper<MediaFile> getMediaBuilder() {
        return row -> MediaFile.builder()
                .id(row.getLong("id"))
                .name(row.getString("filename"))
                .mimeType(row.getString("mime_type"))
                .build();
    }
}
