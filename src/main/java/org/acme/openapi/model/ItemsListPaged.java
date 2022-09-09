package org.acme.openapi.model;

import java.util.ArrayList;
import java.util.List;
import org.acme.openapi.model.ItemListEntry;
import org.acme.openapi.model.LinksPaged;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsListPaged  {

    private LinksPaged links;
    private List<ItemListEntry> items = null;

    /**
    * Get links
    * @return links
    **/
    @JsonProperty("_links")
    public LinksPaged getLinks() {
        return links;
    }

    /**
     * Set links
     **/
    public void setLinks(LinksPaged links) {
        this.links = links;
    }

    public ItemsListPaged links(LinksPaged links) {
        this.links = links;
        return this;
    }

    /**
    * Get items
    * @return items
    **/
    @JsonProperty("items")
    public List<ItemListEntry> getItems() {
        return items;
    }

    /**
     * Set items
     **/
    public void setItems(List<ItemListEntry> items) {
        this.items = items;
    }

    public ItemsListPaged items(List<ItemListEntry> items) {
        this.items = items;
        return this;
    }
    public ItemsListPaged addItemsItem(ItemListEntry itemsItem) {
        this.items.add(itemsItem);
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ItemsListPaged {\n");

        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    items: ").append(toIndentedString(items)).append("\n");
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