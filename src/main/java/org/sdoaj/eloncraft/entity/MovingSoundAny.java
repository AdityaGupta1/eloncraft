package org.sdoaj.eloncraft.entity;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Predicate;

@SideOnly(Side.CLIENT)
public class MovingSoundAny extends MovingSound {
    private final Entity entity;

    private final Predicate<Entity> stopCondition;

    public MovingSoundAny(Entity entity, SoundEvent sound, SoundCategory category, double volume, Predicate<Entity> stopCondition) {
        super(sound, category);
        this.entity = entity;
        this.attenuationType = AttenuationType.NONE;
        this.repeat = false;
        this.volume = (float) volume;

        this.stopCondition = stopCondition;
    }

    public MovingSoundAny(Entity entity, SoundEvent sound, SoundCategory category, double volume) {
        this(entity, sound, category, volume, e -> e.isDead);
    }

    public void update() {
        if (entity == null || stopCondition.test(entity)) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) entity.posX;
            this.yPosF = (float) entity.posY;
            this.zPosF = (float) entity.posZ;
            System.out.println(new Vec3d(this.xPosF, this.yPosF, this.zPosF));
        }
    }
}