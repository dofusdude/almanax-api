package org.acme.openapi.model;

import java.util.ArrayList;
import java.util.List;
import org.acme.openapi.model.ConditionEntry;
import org.acme.openapi.model.EffectsEntry;
import org.acme.openapi.model.ImageUrls;
import org.acme.openapi.model.ItemsListEntryTypedType;
import org.acme.openapi.model.RecipeEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cosmetic  {

    @JsonProperty("ankama_id")
    private Integer ankamaId;
    private String name;
    private String description;
    private ItemsListEntryTypedType type;
    private Integer level;
    private Integer pods;

    @JsonProperty("image_urls")
    private ImageUrls imageUrls;

    @JsonProperty("has_effects")
    private Boolean hasEffects;
    private List<EffectsEntry> effects = null;

    @JsonProperty("has_conditions")
    private Boolean hasConditions;
    private List<ConditionEntry> conditions = null;

    @JsonProperty("has_recipe")
    private Boolean hasRecipe;
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

    public Cosmetic ankamaId(Integer ankamaId) {
        this.ankamaId = ankamaId;
        return this;
    }

    /**
    * Get name
    * @return name
    **/
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Set name
     **/
    public void setName(String name) {
        this.name = name;
    }

    public Cosmetic name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get description
    * @return description
    **/
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     **/
    public void setDescription(String description) {
        this.description = description;
    }

    public Cosmetic description(String description) {
        this.description = description;
        return this;
    }

    /**
    * Get type
    * @return type
    **/
    @JsonProperty("type")
    public ItemsListEntryTypedType getType() {
        return type;
    }

    /**
     * Set type
     **/
    public void setType(ItemsListEntryTypedType type) {
        this.type = type;
    }

    public Cosmetic type(ItemsListEntryTypedType type) {
        this.type = type;
        return this;
    }

    /**
    * Get level
    * @return level
    **/
    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    /**
     * Set level
     **/
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Cosmetic level(Integer level) {
        this.level = level;
        return this;
    }

    /**
    * Get pods
    * @return pods
    **/
    @JsonProperty("pods")
    public Integer getPods() {
        return pods;
    }

    /**
     * Set pods
     **/
    public void setPods(Integer pods) {
        this.pods = pods;
    }

    public Cosmetic pods(Integer pods) {
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

    public Cosmetic imageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
    * Get hasEffects
    * @return hasEffects
    **/
    public Boolean getHasEffects() {
        return hasEffects;
    }

    /**
     * Set hasEffects
     **/
    public void setHasEffects(Boolean hasEffects) {
        this.hasEffects = hasEffects;
    }

    public Cosmetic hasEffects(Boolean hasEffects) {
        this.hasEffects = hasEffects;
        return this;
    }

    /**
    * Get effects
    * @return effects
    **/
    @JsonProperty("effects")
    public List<EffectsEntry> getEffects() {
        return effects;
    }

    /**
     * Set effects
     **/
    public void setEffects(List<EffectsEntry> effects) {
        this.effects = effects;
    }

    public Cosmetic effects(List<EffectsEntry> effects) {
        this.effects = effects;
        return this;
    }
    public Cosmetic addEffectsItem(EffectsEntry effectsItem) {
        this.effects.add(effectsItem);
        return this;
    }

    /**
    * Get hasConditions
    * @return hasConditions
    **/
    @JsonProperty("has_conditions")
    public Boolean getHasConditions() {
        return hasConditions;
    }

    /**
     * Set hasConditions
     **/
    public void setHasConditions(Boolean hasConditions) {
        this.hasConditions = hasConditions;
    }

    public Cosmetic hasConditions(Boolean hasConditions) {
        this.hasConditions = hasConditions;
        return this;
    }

    /**
    * Get conditions
    * @return conditions
    **/
    @JsonProperty("conditions")
    public List<ConditionEntry> getConditions() {
        return conditions;
    }

    /**
     * Set conditions
     **/
    public void setConditions(List<ConditionEntry> conditions) {
        this.conditions = conditions;
    }

    public Cosmetic conditions(List<ConditionEntry> conditions) {
        this.conditions = conditions;
        return this;
    }
    public Cosmetic addConditionsItem(ConditionEntry conditionsItem) {
        this.conditions.add(conditionsItem);
        return this;
    }

    /**
    * Get hasRecipe
    * @return hasRecipe
    **/
    @JsonProperty("has_recipe")
    public Boolean getHasRecipe() {
        return hasRecipe;
    }

    /**
     * Set hasRecipe
     **/
    public void setHasRecipe(Boolean hasRecipe) {
        this.hasRecipe = hasRecipe;
    }

    public Cosmetic hasRecipe(Boolean hasRecipe) {
        this.hasRecipe = hasRecipe;
        return this;
    }

    /**
    * Get recipe
    * @return recipe
    **/
    @JsonProperty("recipe")
    public List<RecipeEntry> getRecipe() {
        return recipe;
    }

    /**
     * Set recipe
     **/
    public void setRecipe(List<RecipeEntry> recipe) {
        this.recipe = recipe;
    }

    public Cosmetic recipe(List<RecipeEntry> recipe) {
        this.recipe = recipe;
        return this;
    }
    public Cosmetic addRecipeItem(RecipeEntry recipeItem) {
        this.recipe.add(recipeItem);
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Cosmetic {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    level: ").append(toIndentedString(level)).append("\n");
        sb.append("    pods: ").append(toIndentedString(pods)).append("\n");
        sb.append("    imageUrls: ").append(toIndentedString(imageUrls)).append("\n");
        sb.append("    hasEffects: ").append(toIndentedString(hasEffects)).append("\n");
        sb.append("    effects: ").append(toIndentedString(effects)).append("\n");
        sb.append("    hasConditions: ").append(toIndentedString(hasConditions)).append("\n");
        sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
        sb.append("    hasRecipe: ").append(toIndentedString(hasRecipe)).append("\n");
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