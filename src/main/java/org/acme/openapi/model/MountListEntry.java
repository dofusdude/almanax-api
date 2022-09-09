package org.acme.openapi.model;

import org.acme.openapi.model.ImageUrls;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MountListEntry  {


    @JsonProperty("ankama_id")
    private Integer ankamaId;
    private String name;

    @JsonProperty("family_namye")
    private String familyName;

    @JsonProperty("image_urls")
    private ImageUrls imageUrls;

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

    public MountListEntry ankamaId(Integer ankamaId) {
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

    public MountListEntry name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get familyName
    * @return familyName
    **/
    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Set familyName
     **/
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public MountListEntry familyName(String familyName) {
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

    public MountListEntry imageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MountListEntry {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    familyName: ").append(toIndentedString(familyName)).append("\n");
        sb.append("    imageUrls: ").append(toIndentedString(imageUrls)).append("\n");
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