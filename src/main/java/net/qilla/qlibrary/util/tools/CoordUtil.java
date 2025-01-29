package net.qilla.qlibrary.util.tools;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public final class CoordUtil {

    /**
     * Utility method for obtaining a block's position within the current chunk.
     The method encodes the chunk-relative Y position in the first 7 digits of the integer.
     Note: This allows 255 unique Y level chunks. It then stores the block's sub-chunk
     coordinates: X in the next 4 bits, Y in the subsequent 4 bits, and Z in the final 4 bits.
     * @param x The block's X coordinate
     * @param y The block's Y coordinate
     * @param z The block's Z coordinate
     * @return Returns the blocks location within the current thunk
     */

    public static int getSubChunkKey(int x, int y, int z) {
        int chunkY = y >> 4;

        int subChunkX = x & 15;
        int subChunkY = y & 15;
        int subChunkZ = z & 15;

        return (chunkY & 255) << 12 | (subChunkX << 8) | (subChunkY << 4) | subChunkZ;
    }

    /**
     * Utility method for obtaining a block's position within the current chunk
     * @param blockPos The block position to lookup
     * @return Returns the blocks location within the current thunk
     */

    public static int getSubChunkKey(@NotNull BlockPos blockPos) {
        Preconditions.checkNotNull(blockPos, "BlockPos cannot be null");
        return getSubChunkKey(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    /**
     * Utility method for obtaining a chunk's position within the world
     * @param x The chunk's X coordinate
     * @param z The chunk's Z coordinate
     * @return Returns the chunk's unique location within the world
     */

    public static long getChunkKey(int x, int z) {
        return ((long) x) & 0xFFFFFFFFL | (((long) z) & 0xFFFFFFFFL) << 32;
    }

    /**
     * Utility method for obtaining a chunk's position within the world
     * @param blockPos The block position to lookup
     * @return Returns the chunks unique location within a long
     */

    public static long getChunkKey(@NotNull BlockPos blockPos) {
        Preconditions.checkNotNull(blockPos, "BlockPos cannot be null");

        return getChunkKey(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    /**
     * Utility method for converting a chunkKey and a subChunkKey into
     * a BlockPos object.
     * @param chunkKey A unique key for a chunk's position
     * @param subChunkKey An integer that represents a block's sub-chunk coordinate
     * within said chunk
     * @return Returns a new Block Position object
     */

    @NotNull
    public static BlockPos getBlockPos(long chunkKey, int subChunkKey) {
        int chunkX = (int) (chunkKey & 0xFFFFFFFFL);
        int chunkZ = (int) ((chunkKey >> 32) & 0xFFFFFFFFL);

        int chunkY = (subChunkKey >> 12) & 0x1FF;

        int subChunkX = (subChunkKey >> 8) & 0xF;
        int subChunkY = (subChunkKey >> 4) & 0xF;
        int subChunkZ = (subChunkKey) & 0xF;

        int x = (chunkX << 4) | subChunkX;
        int y = (chunkY << 4) | subChunkY;
        int z = (chunkZ << 4) | subChunkZ;

        return new BlockPos(x, y, z);
    }

    @NotNull
    public static BlockPos getBlockPos(@NotNull Location loc) {
        Preconditions.checkNotNull(loc, "Location cannot be null");

        return new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @NotNull
    public static BlockPos getBlockPos(@NotNull Block block) {
        Preconditions.checkNotNull(block, "Block cannot be null");
        return new BlockPos(block.getX(), block.getY(), block.getZ());
    }

    @NotNull
    public static Location getLoc(@NotNull BlockPos blockPos, @NotNull World world) {
        Preconditions.checkNotNull(blockPos, "BlockPos cannot be null");
        Preconditions.checkNotNull(world, "World cannot be null");
        return new Location(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @NotNull
    public static Block getBlock(@NotNull BlockPos blockPos, @NotNull World world) {
        Preconditions.checkNotNull(blockPos, "BlockPos cannot be null");
        Preconditions.checkNotNull(world, "World cannot be null");
        return world.getBlockAt(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}