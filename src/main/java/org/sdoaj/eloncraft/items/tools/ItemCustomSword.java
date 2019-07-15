package org.sdoaj.eloncraft.items.tools;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.sdoaj.eloncraft.items.ItemBasic;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class ItemCustomSword extends ItemSword {
    private double speed = Double.NaN;
    private double damage = Double.NaN;

    public ItemCustomSword(String name, ToolMaterial material) {
        super(material);
        ItemBasic.initItem(this, name);
    }

    public ItemCustomSword setSpeed(double speed) {
        this.speed = speed - 4.0;
        return this;
    }

    public ItemCustomSword setDamage(double damage) {
        this.damage = damage - 1.0;
        return this;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

        if (slot == EntityEquipmentSlot.MAINHAND) {
            if (!Double.isNaN(speed)) {
                replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, speed);
            }

            if (!Double.isNaN(damage)) {
                replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, damage);
            }
        }

        return modifiers;
    }

    private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double newValue) {
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

        modifierOptional.ifPresent(modifier -> {
            modifiers.remove(modifier);
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), newValue, modifier.getOperation()));
        });
    }
}
