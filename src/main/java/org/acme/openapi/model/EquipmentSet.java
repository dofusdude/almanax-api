package org.acme.openapi.model;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;


public class EquipmentSet  {


    @JsonbProperty("ankama_id")
    private Integer ankamaId;
    private String name;

    @JsonbProperty("equipment_ids")
    private List<Integer> equipmentIds = null;
    private List<List<EffectsEntry>> effects = null;
    private Integer level;

    /**
    * Get ankamaId
    * @return ankamaId
    **/
    public Integer getAnkamaId() {
        return ankamaId;
    }

    /**
     * Set ankamaId
     **/
    public void setAnkamaId(Integer ankamaId) {
        this.ankamaId = ankamaId;
    }

    public EquipmentSet ankamaId(Integer ankamaId) {
        this.ankamaId = ankamaId;
        return this;
    }

    /**
    * Get name
    * @return name
    **/
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Set name
     **/
    public void setName(String name) {
        this.name = name;
    }

    public EquipmentSet name(String name) {
        this.name = name;
        return this;
    }

    /**
    * Get equipmentIds
    * @return equipmentIds
    **/
    public List<Integer> getEquipmentIds() {
        return equipmentIds;
    }

    /**
     * Set equipmentIds
     **/
    public void setEquipmentIds(List<Integer> equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public EquipmentSet equipmentIds(List<Integer> equipmentIds) {
        this.equipmentIds = equipmentIds;
        return this;
    }
    public EquipmentSet addEquipmentIdsItem(Integer equipmentIdsItem) {
        this.equipmentIds.add(equipmentIdsItem);
        return this;
    }

    /**
    * Get effects
    * @return effects
    **/
    @JsonbProperty("effects")
    public List<List<EffectsEntry>> getEffects() {
        return effects;
    }

    /**
     * Set effects
     **/
    public void setEffects(List<List<EffectsEntry>> effects) {
        this.effects = effects;
    }

    public EquipmentSet effects(List<List<EffectsEntry>> effects) {
        this.effects = effects;
        return this;
    }
    public EquipmentSet addEffectsItem(List<EffectsEntry> effectsItem) {
        this.effects.add(effectsItem);
        return this;
    }

    /**
    * Get level
    * @return level
    **/
    @JsonbProperty("level")
    public Integer getLevel() {
        return level;
    }

    /**
     * Set level
     **/
    public void setLevel(Integer level) {
        this.level = level;
    }

    public EquipmentSet level(Integer level) {
        this.level = level;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EquipmentSet {\n");

        sb.append("    ankamaId: ").append(toIndentedString(ankamaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    equipmentIds: ").append(toIndentedString(equipmentIds)).append("\n");
        sb.append("    effects: ").append(toIndentedString(effects)).append("\n");
        sb.append("    level: ").append(toIndentedString(level)).append("\n");
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