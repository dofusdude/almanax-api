package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeEntry  {


    @JsonProperty("item_ankama_id")
    private Integer itemAnkamaId;

    @JsonProperty("item_subtype")
    private String itemSubtype;
    private Integer quantity;

    /**
    * Get itemAnkamaId
    * @return itemAnkamaId
    **/
    public Integer getItemAnkamaId() {
        return itemAnkamaId;
    }

    /**
     * Set itemAnkamaId
     **/
    public void setItemAnkamaId(Integer itemAnkamaId) {
        this.itemAnkamaId = itemAnkamaId;
    }

    public RecipeEntry itemAnkamaId(Integer itemAnkamaId) {
        this.itemAnkamaId = itemAnkamaId;
        return this;
    }

    /**
    * Get itemSubtype
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

    public RecipeEntry itemSubtype(String itemSubtype) {
        this.itemSubtype = itemSubtype;
        return this;
    }

    /**
    * Get quantity
    * @return quantity
    **/
    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Set quantity
     **/
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public RecipeEntry quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecipeEntry {\n");

        sb.append("    itemAnkamaId: ").append(toIndentedString(itemAnkamaId)).append("\n");
        sb.append("    itemSubtype: ").append(toIndentedString(itemSubtype)).append("\n");
        sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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