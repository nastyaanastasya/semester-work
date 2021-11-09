package ru.kpfu.repositories;

import ru.kpfu.models.Ingredient;
import ru.kpfu.utils.RowMapper;

import java.util.List;

public class IngredientsRepository {
    private JdbcTemplate<Ingredient> source;
    private RowMapper<Ingredient> builder;

    private final String SQL_FIND_BY_ID =
            "select * from public.ingredient where id = ?";

    private final String SQL_UPDATE =
            "update public.ingredient set (name, amount, unit) = (?, ?, ?)";

    private final String SQL_SAVE =
            "insert into public.ingredient (recipe_id, name, amount, unit) values (?, ?, ?, ?) returning id";

    private final String SQL_DELETE =
            "delete from public.ingredient where id = ?";

    private final String SQL_FIND_BY_RECIPE_ID = "select i.id, i.name, i.amount, i.unit " +
            "from public.ingredient as i " +
            "where i.recipe_id = ?";

    public IngredientsRepository(String driver, String url, String user, String pass) {
        builder = getIngredientBuilder();
        this.source = new JdbcTemplate<>(driver, url, user, pass);
    }

    public Ingredient findById(long id) {
        List<Ingredient> list = source.query(SQL_FIND_BY_ID, builder, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public void update(Ingredient ingredient) {
        source.update(SQL_UPDATE, ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
    }

    public long save(Ingredient ingredient, long recipeID) {
        return source.query(
                SQL_SAVE, row->row.getLong("id"), recipeID, ingredient.getName(), ingredient.getAmount(), ingredient.getUnit()
        ).get(0);
    }

    public void delete(long id) {
        source.update(SQL_DELETE, id);
    }

    public List<Ingredient> findByRecipeId(long id) {
        List<Ingredient> list = source.query(SQL_FIND_BY_RECIPE_ID, builder, id);
        return list.size() > 0 ? list : null;
    }

    private RowMapper<Ingredient> getIngredientBuilder() {
        return row -> Ingredient.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .amount(row.getInt("amount"))
                .unit(row.getString("unit"))
                .build();
    }
}
