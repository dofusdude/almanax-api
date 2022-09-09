package org.acme.openapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.bind.annotation.JsonbProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
  * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
 **/
public class ImageUrls  {

    /**
      * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    private String icon;
    /**
      * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    private String sd;
    /**
      * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    private String hq;
    /**
      * All images except icon are rendered in the background which can take some time (up to hours if all data is completely generated from scratch). Because of this, they can be null if they are not yet rendered.
     **/
    private String hd;

    /**
    * 60x60 px, always available
    * @return icon
    **/
    @JsonbProperty("icon")
    public String getIcon() {
        return icon;
    }

    /**
     * Set icon
     **/
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ImageUrls icon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
    * 200x200 px
    * @return sd
    **/
    @JsonbProperty("sd")
    public String getSd() {
        return sd;
    }

    /**
     * Set sd
     **/
    public void setSd(String sd) {
        this.sd = sd;
    }

    public ImageUrls sd(String sd) {
        this.sd = sd;
        return this;
    }

    /**
    * 400x400 px
    * @return hq
    **/
    @JsonbProperty("hq")
    public String getHq() {
        return hq;
    }

    /**
     * Set hq
     **/
    public void setHq(String hq) {
        this.hq = hq;
    }

    public ImageUrls hq(String hq) {
        this.hq = hq;
        return this;
    }

    /**
    * 800x800 px
    * @return hd
    **/
    @JsonbProperty("hd")
    public String getHd() {
        return hd;
    }

    /**
     * Set hd
     **/
    public void setHd(String hd) {
        this.hd = hd;
    }

    public ImageUrls hd(String hd) {
        this.hd = hd;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ImageUrls {\n");

        sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
        sb.append("    sd: ").append(toIndentedString(sd)).append("\n");
        sb.append("    hq: ").append(toIndentedString(hq)).append("\n");
        sb.append("    hd: ").append(toIndentedString(hd)).append("\n");
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