package org.acme.openapi.model;

import java.util.ArrayList;
import java.util.List;
import org.acme.openapi.model.ConditionEntry;
import org.acme.openapi.model.EffectsEntry;
import org.acme.openapi.model.EquipmentParentSet;
import org.acme.openapi.model.ImageUrls;
import org.acme.openapi.model.ItemsListEntryTypedType;
import org.acme.openapi.model.RecipeEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.bind.annotation.JsonbProperty;

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
    @JsonbProperty("has_effects")

    private Boolean hasEffects;
    private List<EffectsEntry> effects = null;
    @JsonbProperty("has_conditions")
    private Boolean hasConditions;
    private List<ConditionEntry> conditions = null;
    @JsonbProperty("has_recipe")
    private Boolean hasRecipe;
    private List<RecipeEntry> recipe = null;

    @JsonbProperty("has_parent_set")
    private Boolean hasParentSet;

    @JsonbProperty("parent_set")
    private EquipmentParentSet parentSet;

    /**
    * Get ankamaId
    * @return ankamaId
    **/
    @JsonbProperty("ankama_id")
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
    @JsonbProperty("is_weapon")
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
    @JsonbProperty("image_urls")
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
    * Get hasEffects
    * @return hasEffects
    **/
    @JsonbProperty("has_effects")
    public Boolean getHasEffects() {
        return hasEffects;
    }

    /**
     * Set hasEffects
     **/
    public void setHasEffects(Boolean hasEffects) {
        this.hasEffects = hasEffects;
    }

    public Equipment hasEffects(Boolean hasEffects) {
        this.hasEffects = hasEffects;
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
    * Get hasConditions
    * @return hasConditions
    **/
    @JsonbProperty("has_conditions")
    public Boolean getHasConditions() {
        return hasConditions;
    }

    /**
     * Set hasConditions
     **/
    public void setHasConditions(Boolean hasConditions) {
        this.hasConditions = hasConditions;
    }

    public Equipment hasConditions(Boolean hasConditions) {
        this.hasConditions = hasConditions;
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
    * Get hasRecipe
    * @return hasRecipe
    **/
    @JsonbProperty("has_recipe")
    public Boolean getHasRecipe() {
        return hasRecipe;
    }

    /**
     * Set hasRecipe
     **/
    public void setHasRecipe(Boolean hasRecipe) {
        this.hasRecipe = hasRecipe;
    }

    public Equipment hasRecipe(Boolean hasRecipe) {
        this.hasRecipe = hasRecipe;
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
    * Get hasParentSet
    * @return hasParentSet
    **/
    @JsonbProperty("has_parent_set")
    public Boolean getHasParentSet() {
        return hasParentSet;
    }

    /**
     * Set hasParentSet
     **/
    public void setHasParentSet(Boolean hasParentSet) {
        this.hasParentSet = hasParentSet;
    }

    public Equipment hasParentSet(Boolean hasParentSet) {
        this.hasParentSet = hasParentSet;
        return this;
    }

    /**
    * Get parentSet
    * @return parentSet
    **/
    @JsonbProperty("parent_set")
    public EquipmentParentSet getParentSet() {
        return parentSet;
    }

    /**
     * Set parentSet
     **/
    public void setParentSet(EquipmentParentSet parentSet) {
        this.parentSet = parentSet;
    }

    public Equipment parentSet(EquipmentParentSet parentSet) {
        this.parentSet = parentSet;
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
        sb.append("    hasEffects: ").append(toIndentedString(hasEffects)).append("\n");
        sb.append("    effects: ").append(toIndentedString(effects)).append("\n");
        sb.append("    hasConditions: ").append(toIndentedString(hasConditions)).append("\n");
        sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
        sb.append("    hasRecipe: ").append(toIndentedString(hasRecipe)).append("\n");
        sb.append("    recipe: ").append(toIndentedString(recipe)).append("\n");
        sb.append("    hasParentSet: ").append(toIndentedString(hasParentSet)).append("\n");
        sb.append("    parentSet: ").append(toIndentedString(parentSet)).append("\n");
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