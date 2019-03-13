package org.sdoaj.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public class ItemRangedWeapon extends ItemBasic {
    private final Function<World, Entity> projectileCreator;
    private final int projectiles;
    private final double velocity;
    private final ItemStack ammunition;

    public ItemRangedWeapon(String name, Function<World, Entity> projectileCreator, int projectiles, double velocity, ItemStack ammunition) {
        super(name);
        this.projectileCreator = projectileCreator;
        this.projectiles = projectiles;
        this.velocity = velocity;
        this.ammunition = ammunition;
    }

    private static final Random random = new Random();
    private static final DoubleSupplier randomVelocity = () -> random.nextDouble() / 2;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack self = player.getHeldItem(hand);

        for (int bullets = 0; bullets < this.projectiles; bullets++) {
            // TODO make ammuniton work, maybe see ItemBow for examples
            /*if (!player.capabilities.isCreativeMode) {
                if (ammunition != null) {
                    if (!player.inventory.hasItemStack(ammunition)) {
                        return new ActionResult<>(EnumActionResult.FAIL, self);
                    } else {
                        player.inventory;
                    }
                }
            }*/

            if (!world.isRemote) {
                Entity projectile = projectileCreator.apply(world);
                projectile.setPositionAndRotation(player.posX, player.posY, player.posZ, player.cameraYaw, player.cameraPitch);

                Vec3d look = player.getLookVec().scale(velocity);
                Vec3d norm = look.normalize().scale(velocity);
                Vec3d velocity = new Vec3d(
                        look.x + norm.x * randomVelocity.getAsDouble() + player.motionX,
                        look.y + norm.y * randomVelocity.getAsDouble() + player.motionY,
                        look.z + norm.z * randomVelocity.getAsDouble() + player.motionZ
                );
                projectile.setVelocity(velocity.x, velocity.y, velocity.z);

                world.spawnEntity(projectile);
            }
        }

        if (!player.capabilities.isCreativeMode) {
            if (getDamage(new ItemStack(this)) == getMaxDamage(self)) {
                self.shrink(1);
            } else {
                self.damageItem(1, player);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, self);
    }
}
