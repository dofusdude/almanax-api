package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment  {


    @JsonbProperty("ankama_id")
    private Integer ankamaId;
    private String name;
    private String description;
    private ItemsListEntryTypedType type;

    @JsonbProperty("is_weapon")
    private Boolean isWeapon;
    private Integer level;
    private Integer pods;

    @JsonbProperty("image_urls")
    private ImageUrls imageUrls;
    private List<EffectsEntry> effects = null;
    private List<ConditionEntry> conditions = null;
    private List<RecipeEntry> recipe = null;

    /**
    * Get ankamaId
    * @return ankamaId
    **/
    public Integer getAnkamaId() {
        return ankamaId;
    }

    /**
     * Set ankamaId
     **/
    public void setAnkamaId(Integer ankamaId) {
        this.ankamaId = ankamaId;
    }

    public Equipment ankamaId(Integer ankamaId) {
        this.ankamaId = ankamaId;
        return this;
    }

    /**
    * Get name
    * @return name
    **/
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Set name
     **/
    public void setName(String name) {
        this.name = name;
    }

    public Equipment name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get description
    * @return description
    **/
    @JsonbProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     **/
    public void setDescription(String description) {
        this.description = description;
    }

    public Equipment description(String description) {
        this.description = description;
        return this;
    }

    /**
    * Get type
    * @return type
    **/
    @JsonbProperty("type")
    public ItemsListEntryTypedType getType() {
        return type;
    }

    /**
     * Set type
     **/
    public void setType(ItemsListEntryTypedType type) {
        this.type = type;
    }

    public Equipment type(ItemsListEntryTypedType type) {
        this.type = type;
        return this;
    }

    /**
    * Get isWeapon
    * @return isWeapon
    **/
    public Boolean getIsWeapon() {
        return isWeapon;
    }

    /**
     * Set isWeapon
     **/
    public void setIsWeapon(Boolean isWeapon) {
        this.isWeapon = isWeapon;
    }

    public Equipment isWeapon(Boolean isWeapon) {
        this.isWeapon = isWeapon;
        return this;
    }

    /**
    * Get level
    * @return level
    **/
    @JsonbProperty("level")
    public Integer getLevel() {
        return level;
    }

    /**
     * Set level
     **/
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Equipment level(Integer level) {
        this.level = level;
        return this;
    }

    /**
    * Get pods
    * @return pods
    **/
    @JsonbProperty("pods")
    public Integer getPods() {
        return pods;
    }

    /**
     * Set pods
     **/
    public void setPods(Integer pods) {
        this.pods = pods;
    }

    public Equipment pods(Integer pods) {
        this.pods = pods;
        return this;
    }

    /**
    * Get imageUrls
    * @return imageUrls
    **/
    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    /**
     * Set imageUrls
     **/
    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Equipment imageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
    * Get effects
    * @return effects
    **/
    @JsonbProperty("effects")
    public List<EffectsEntry> getEffects() {
        return effects;
    }

    /**
     * Set effects
     **/
    public void setEffects(List<EffectsEntry> effects) {
        this.effects = effects;
    }

    public Equipment effects(List<EffectsEntry> effects) {
        this.effects = effects;
        return this;
    }
    public Equipment addEffectsItem(EffectsEntry effectsItem) {
        this.effects.add(effectsItem);
        return this;
    }

    /**
    * Get conditions
    * @return conditions
    **/
    @JsonbProperty("conditions")
    public List<ConditionEntry> getConditions() {
        return conditions;
    }

    /**
     * Set conditions
     **/
    public void setConditions(List<ConditionEntry> conditions) {
        this.conditions = conditions;
    }

    public Equipment conditions(List<ConditionEntry> conditions) {
        this.conditions = conditions;
        return this;
    }
    public Equipment addConditionsItem(ConditionEntry conditionsItem) {
        this.conditions.add(conditionsItem);
        return this;
    }

    /**
    * Get recipe
    * @return recipe
    **/
    @JsonbProperty("recipe")
    public List<RecipeEntry> getRecipe() {
        return recipe;
    }

    /**
     * Set recipe
     **/
    public void setRecipe(List<RecipeEntry> recipe) {
        this.recipe = recipe;
    }

    public Equipment recipe(List<RecipeEntry> recipe) {
        this.recipe = recipe;
        return this;
    }
    public Equipment addRecipeItem(RecipeEntry recipeItem) {
        this.recipe.add(recipeItem);
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Equipment {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    isWeapon: ").append(toIndentedString(isWeapon)).append("\n");
        sb.append("    level: ").append(toIndentedString(level)).append("\n");
        sb.append("    pods: ").append(toIndentedString(pods)).append("\n");
        sb.append("    imageUrls: ").append(toIndentedString(imageUrls)).append("\n");
        sb.append("    effects: ").append(toIndentedString(effects)).append("\n");
        sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
        sb.append("    recipe: ").append(toIndentedString(recipe)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}