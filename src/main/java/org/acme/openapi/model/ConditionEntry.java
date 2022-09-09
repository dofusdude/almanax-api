package org.acme.openapi.model;

import org.acme.openapi.model.EffectsEntryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionEntry  {

    private String operator;
    @JsonProperty("int_value")
    private Integer intValue;
    private EffectsEntryType element;

    /**
    * Get operator
    * @return operator
    **/
    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    /**
     * Set operator
     **/
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ConditionEntry operator(String operator) {
        this.operator = operator;
        return this;
    }

    /**
    * Get intValue
    * @return intValue
    **/
    public Integer getIntValue() {
        return intValue;
    }

    /**
     * Set intValue
     **/
    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public ConditionEntry intValue(Integer intValue) {
        this.intValue = intValue;
        return this;
    }

    /**
    * Get element
    * @return element
    **/
    @JsonProperty("element")
    public EffectsEntryType getElement() {
        return element;
    }

    /**
     * Set element
     **/
    public void setElement(EffectsEntryType element) {
        this.element = element;
    }

    public ConditionEntry element(EffectsEntryType element) {
        this.element = element;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ConditionEntry {\n");

        sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
        sb.append("    intValue: ").append(toIndentedString(intValue)).append("\n");
        sb.append("    element: ").append(toIndentedString(element)).append("\n");
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