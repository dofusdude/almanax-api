package org.acme.openapi.model;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;


public class MountsListPaged  {


    @JsonbProperty("_links")
    private LinksPaged links;
    private List<MountListEntry> items = null;

    /**
    * Get links
    * @return links
    **/
    public LinksPaged getLinks() {
        return links;
    }

    /**
     * Set links
     **/
    public void setLinks(LinksPaged links) {
        this.links = links;
    }

    public MountsListPaged links(LinksPaged links) {
        this.links = links;
        return this;
    }

    /**
    * Get items
    * @return items
    **/
    @JsonbProperty("items")
    public List<MountListEntry> getItems() {
        return items;
    }

    /**
     * Set items
     **/
    public void setItems(List<MountListEntry> items) {
        this.items = items;
    }

    public MountsListPaged items(List<MountListEntry> items) {
        this.items = items;
        return this;
    }
    public MountsListPaged addItemsItem(MountListEntry itemsItem) {
        this.items.add(itemsItem);
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MountsListPaged {\n");

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