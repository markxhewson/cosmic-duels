package tech.markxhewson.duels.manager.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import javax.annotation.Nullable;
import java.util.Random;

public class VoidWorldGenerator extends ChunkGenerator {

    @Override
    public void generateNoise(@Nullable WorldInfo worldInfo, @Nullable Random random, int x, int z, @Nullable ChunkData chunkData) {
        if (x == 0 && z == 0) {
            assert chunkData != null;
            chunkData.setBlock(0, 64, 0, Material.BEDROCK);
        }
    }

    @Override
    public Location getFixedSpawnLocation(@Nullable World world, @Nullable Random random) {
        return new Location(world, 0, 64, 0);
    }

}