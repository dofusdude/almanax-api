package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.bind.annotation.JsonbProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeaponRange  {

    private Integer min;
    private Integer max;

    /**
    * Get min
    * @return min
    **/
    @JsonbProperty("min")
    public Integer getMin() {
        return min;
    }

    /**
     * Set min
     **/
    public void setMin(Integer min) {
        this.min = min;
    }

    public WeaponRange min(Integer min) {
        this.min = min;
        return this;
    }

    /**
    * Get max
    * @return max
    **/
    @JsonbProperty("max")
    public Integer getMax() {
        return max;
    }

    /**
     * Set max
     **/
    public void setMax(Integer max) {
        this.max = max;
    }

    public WeaponRange max(Integer max) {
        this.max = max;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WeaponRange {\n");

        sb.append("    min: ").append(toIndentedString(min)).append("\n");
        sb.append("    max: ").append(toIndentedString(max)).append("\n");
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