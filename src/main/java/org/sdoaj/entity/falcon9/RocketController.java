package org.sdoaj.entity.falcon9;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class RocketController {
    private final EntityFalcon9Stage1 stage1;
    private final EntityFalcon9Stage2 stage2;

    private Position stage1Pos;
    private Position stage2Pos;

    public RocketController(EntityFalcon9Stage1 stage1, EntityFalcon9Stage2 stage2) {
        this.stage1 = stage1;
        this.stage2 = stage2;
    }

    private void setLocationAndAngles(Entity entity, Position pos) {
        entity.setLocationAndAngles(pos.x, pos.y, pos.z, pos.yaw, pos.pitch);
    }

    public void setPosition(double x, double y, double z, float yaw, float pitch) {
        stage1Pos = new Position(x, y, z, yaw, pitch);
        setLocationAndAngles(stage1, stage1Pos);

        stage2Pos = new Position(x, y + 58.9 / 2.0 + (28.0 / 16.0 * ModelFalcon9Stage1.modelScale) + 0.125, z, yaw, pitch);
        setLocationAndAngles(stage2, stage2Pos);
    }

    public void setPosition(Vec3d pos, float yaw, float pitch) {
        setPosition(pos.x, pos.y, pos.z, yaw, pitch);
    }

    Position getStage1Pos() {
        return stage1Pos;
    }

    Position getStage2Pos() {
        return stage2Pos;
    }
}

class Position {
    public final double x;
    public final double y;
    public final double z;
    public final float yaw;
    public final float pitch;

    Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    Position(Vec3d pos, float yaw, float pitch) {
        this(pos.x, pos.y, pos.z, yaw, pitch);
    }
}
