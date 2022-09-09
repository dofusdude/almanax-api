package org.acme.openapi.model;

import java.util.ArrayList;
import java.util.List;
import org.acme.openapi.model.EffectsEntry;
import org.acme.openapi.model.ImageUrls;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Mount  {

    @JsonProperty("ankama_id")
    private Integer ankamaId;
    private String name;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("image_urls")
    private ImageUrls imageUrls;

    @JsonProperty("has_effects")
    private Boolean hasEffects;
    private List<EffectsEntry> effects = null;

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

    public Mount ankamaId(Integer ankamaId) {
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

    public Mount name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get familyName
    * @return familyName
    **/
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Set familyName
     **/
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Mount familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    /**
    * Get imageUrls
    * @return imageUrls
    **/
    @JsonProperty("image_urls")
    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    /**
     * Set imageUrls
     **/
    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Mount imageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
    * Get hasEffects
    * @return hasEffects
    **/
    @JsonProperty("has_effects")
    public Boolean getHasEffects() {
        return hasEffects;
    }

    /**
     * Set hasEffects
     **/
    public void setHasEffects(Boolean hasEffects) {
        this.hasEffects = hasEffects;
    }

    public Mount hasEffects(Boolean hasEffects) {
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

    public Mount effects(List<EffectsEntry> effects) {
        this.effects = effects;
        return this;
    }
    public Mount addEffectsItem(EffectsEntry effectsItem) {
        this.effects.add(effectsItem);
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Mount {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    familyName: ").append(toIndentedString(familyName)).append("\n");
        sb.append("    imageUrls: ").append(toIndentedString(imageUrls)).append("\n");
        sb.append("    hasEffects: ").append(toIndentedString(hasEffects)).append("\n");
        sb.append("    effects: ").append(toIndentedString(effects)).append("\n");
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