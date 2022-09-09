package org.acme.openapi.model;

import org.acme.openapi.model.ImageUrls;
import org.acme.openapi.model.ItemsListEntryTypedType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsListEntryTyped  {

    @JsonProperty("ankama_id")
    private Integer ankamaId;
    private String name;
    private ItemsListEntryTypedType type;

    @JsonProperty("item_subtype")
    private String itemSubtype;
    private Integer level;
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

    public ItemsListEntryTyped ankamaId(Integer ankamaId) {
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

    public ItemsListEntryTyped name(String name) {
        this.name = name;
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

    public ItemsListEntryTyped type(ItemsListEntryTypedType type) {
        this.type = type;
        return this;
    }

    /**
    * The API item category. Can be used to build the request URL for this particular item. Always english.
    * @return itemSubtype
    **/
    @JsonProperty("item_subtype")
    public String getItemSubtype() {
        return itemSubtype;
    }

    /**
     * Set itemSubtype
     **/
    public void setItemSubtype(String itemSubtype) {
        this.itemSubtype = itemSubtype;
    }

    public ItemsListEntryTyped itemSubtype(String itemSubtype) {
        this.itemSubtype = itemSubtype;
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

    public ItemsListEntryTyped level(Integer level) {
        this.level = level;
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

    public ItemsListEntryTyped imageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ItemsListEntryTyped {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    itemSubtype: ").append(toIndentedString(itemSubtype)).append("\n");
        sb.append("    level: ").append(toIndentedString(level)).append("\n");
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