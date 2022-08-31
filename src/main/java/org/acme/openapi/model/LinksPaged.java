package org.acme.openapi.model;

import javax.json.bind.annotation.JsonbProperty;


public class LinksPaged  {

    private String first;
    private String prev;
    private String next;
    private String last;

    /**
    * Get first
    * @return first
    **/
    @JsonbProperty("first")
    public String getFirst() {
        return first;
    }

    /**
     * Set first
     **/
    public void setFirst(String first) {
        this.first = first;
    }

    public LinksPaged first(String first) {
        this.first = first;
        return this;
    }

    /**
    * Get prev
    * @return prev
    **/
    @JsonbProperty("prev")
    public String getPrev() {
        return prev;
    }

    /**
     * Set prev
     **/
    public void setPrev(String prev) {
        this.prev = prev;
    }

    public LinksPaged prev(String prev) {
        this.prev = prev;
        return this;
    }

    /**
    * Get next
    * @return next
    **/
    @JsonbProperty("next")
    public String getNext() {
        return next;
    }

    /**
     * Set next
     **/
    public void setNext(String next) {
        this.next = next;
    }

    public LinksPaged next(String next) {
        this.next = next;
        return this;
    }

    /**
    * Get last
    * @return last
    **/
    @JsonbProperty("last")
    public String getLast() {
        return last;
    }

    /**
     * Set last
     **/
    public void setLast(String last) {
        this.last = last;
    }

    public LinksPaged last(String last) {
        this.last = last;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LinksPaged {\n");

        sb.append("    first: ").append(toIndentedString(first)).append("\n");
        sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
        sb.append("    next: ").append(toIndentedString(next)).append("\n");
        sb.append("    last: ").append(toIndentedString(last)).append("\n");
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