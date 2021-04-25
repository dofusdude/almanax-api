/*
 * Copyright 2021 Christopher Sieh (stelzo@steado.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dofusdu.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {
        @Index(columnList = "date", name = "idx_date", unique = true)
})
public class Offering {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date",
            nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer itemQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Bonus bonus;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    public boolean contextualEqual(Offering o) {
        return o.getBonus().equals(getBonus()) &&
                o.getItemQuantity().equals(getItemQuantity()) &&
                o.getItem().equals(getItem());
    }

    public Offering(Date date, Integer itemQuantity, Bonus bonus, Item item) {
        this.date = date;
        this.itemQuantity = itemQuantity;
        this.bonus = bonus;
        this.item = item;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Offering() {

    }

    public Bonus getBonus() {
        return bonus;
    }

    public Date getDate() {
        return date;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public Item getItem() {
        return item;
    }
}
