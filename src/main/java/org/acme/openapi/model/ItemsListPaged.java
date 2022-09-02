package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsListPaged  {

    private LinksPaged links;
    private List<ItemListEntry> items = null;

    /**
    * Get links
    * @return links
    **/
    @JsonbProperty("_links")
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
    @JsonbProperty("items")
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