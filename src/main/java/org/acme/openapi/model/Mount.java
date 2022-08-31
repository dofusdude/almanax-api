package org.acme.openapi.model;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;


public class Mount  {


    @JsonbProperty("ankama_id")
    private Integer ankamaId;
    private String name;

    @JsonbProperty("family_name")
    private String familyName;

    @JsonbProperty("image_urls")
    private ImageUrls imageUrls;
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