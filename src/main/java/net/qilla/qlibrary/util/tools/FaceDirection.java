package net.qilla.qlibrary.util.tools;

public enum FaceDirection {
    NORTH(0, -180),
    NORTH_EAST(0, -135),
    EAST(0, -90),
    SOUTH_EAST(0, -45),
    SOUTH(0, 0),
    SOUTH_WEST(0, 45),
    WEST(0, 90),
    NORTH_WEST(0, 135),
    UPWARDS(-90, 0),
    DOWNWARDS(90, 0);

    private final double pitch;
    private final double yaw;

    FaceDirection(double pitch, double yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }
}