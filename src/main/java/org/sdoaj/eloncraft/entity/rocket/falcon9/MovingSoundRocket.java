package org.sdoaj.eloncraft.entity.rocket.falcon9;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.entity.rocket.EntityRocketPart;

@SideOnly(Side.CLIENT)
public class MovingSoundRocket extends MovingSound {
    private final EntityRocketPart part;

    public MovingSoundRocket(EntityRocketPart part, SoundEvent sound) {
        super(sound, SoundCategory.NEUTRAL);
        this.part = part;
        this.repeat = false;
    }

    public void update() {
        if (part.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) part.posX;
            this.yPosF = (float) part.posY;
            this.zPosF = (float) part.posZ;
            this.volume = 0.5F;
        }
    }
}