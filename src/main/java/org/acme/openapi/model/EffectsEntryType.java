package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EffectsEntryType  {

    private String name;
    private Integer id;

    @JsonProperty("is_meta")
    private Boolean isMeta;

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

    public EffectsEntryType name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get id
    * @return id
    **/
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Set id
     **/
    public void setId(Integer id) {
        this.id = id;
    }

    public EffectsEntryType id(Integer id) {
        this.id = id;
        return this;
    }

    /**
    * true if a type is generated from the Api instead of Ankama. In that case, always prefer showing the templated string and omit everything else. The \"name\" field will have an english description of the meta type. An example for such effects are class sets effects.
    * @return isMeta
    **/
    @JsonProperty("is_meta")
    public Boolean getIsMeta() {
        return isMeta;
    }

    /**
     * Set isMeta
     **/
    public void setIsMeta(Boolean isMeta) {
        this.isMeta = isMeta;
    }

    public EffectsEntryType isMeta(Boolean isMeta) {
        this.isMeta = isMeta;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EffectsEntryType {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    isMeta: ").append(toIndentedString(isMeta)).append("\n");
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