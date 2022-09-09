package org.acme.openapi.model;

import org.acme.openapi.model.EffectsEntryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EffectsEntry  {

    @JsonProperty("int_minimum")
    private Integer intMinimum;
    @JsonProperty("int_maximum")
    private Integer intMaximum;
    private EffectsEntryType type;

    @JsonProperty("ignore_int_min")
    private Boolean ignoreIntMin;

    @JsonProperty("ignore_int_max")
    private Boolean ignoreIntMax;
    private String formatted;

    /**
    * minimum int value, can be a single if ignore_int_max and no ignore_int_min
    * @return intMinimum
    **/

    public Integer getIntMinimum() {
        return intMinimum;
    }

    /**
     * Set intMinimum
     **/
    public void setIntMinimum(Integer intMinimum) {
        this.intMinimum = intMinimum;
    }

    public EffectsEntry intMinimum(Integer intMinimum) {
        this.intMinimum = intMinimum;
        return this;
    }

    /**
    * maximum int value, if not ignore_int_max and not ignore_int_min, the effect has a range value
    * @return intMaximum
    **/
    @JsonProperty("int_maximum")
    public Integer getIntMaximum() {
        return intMaximum;
    }

    /**
     * Set intMaximum
     **/
    public void setIntMaximum(Integer intMaximum) {
        this.intMaximum = intMaximum;
    }

    public EffectsEntry intMaximum(Integer intMaximum) {
        this.intMaximum = intMaximum;
        return this;
    }

    /**
    * Get type
    * @return type
    **/
    @JsonProperty("type")
    public EffectsEntryType getType() {
        return type;
    }

    /**
     * Set type
     **/
    public void setType(EffectsEntryType type) {
        this.type = type;
    }

    public EffectsEntry type(EffectsEntryType type) {
        this.type = type;
        return this;
    }

    /**
    * ignore the int min field because the actual value is a string. For readability the templated field is the only important field in this case. 
    * @return ignoreIntMin
    **/
    @JsonProperty("ignore_int_min")
    public Boolean getIgnoreIntMin() {
        return ignoreIntMin;
    }

    /**
     * Set ignoreIntMin
     **/
    public void setIgnoreIntMin(Boolean ignoreIntMin) {
        this.ignoreIntMin = ignoreIntMin;
    }

    public EffectsEntry ignoreIntMin(Boolean ignoreIntMin) {
        this.ignoreIntMin = ignoreIntMin;
        return this;
    }

    /**
    * ignore the int max field, if ignore_int_min is true, int min is a single value
    * @return ignoreIntMax
    **/
    @JsonProperty("ignore_int_max")
    public Boolean getIgnoreIntMax() {
        return ignoreIntMax;
    }

    /**
     * Set ignoreIntMax
     **/
    public void setIgnoreIntMax(Boolean ignoreIntMax) {
        this.ignoreIntMax = ignoreIntMax;
    }

    public EffectsEntry ignoreIntMax(Boolean ignoreIntMax) {
        this.ignoreIntMax = ignoreIntMax;
        return this;
    }

    /**
    * all fields from above encoded in a single string
    * @return formatted
    **/
    @JsonProperty("formatted")
    public String getFormatted() {
        return formatted;
    }

    /**
     * Set formatted
     **/
    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public EffectsEntry formatted(String formatted) {
        this.formatted = formatted;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EffectsEntry {\n");

        sb.append("    intMinimum: ").append(toIndentedString(intMinimum)).append("\n");
        sb.append("    intMaximum: ").append(toIndentedString(intMaximum)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    ignoreIntMin: ").append(toIndentedString(ignoreIntMin)).append("\n");
        sb.append("    ignoreIntMax: ").append(toIndentedString(ignoreIntMax)).append("\n");
        sb.append("    formatted: ").append(toIndentedString(formatted)).append("\n");
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