package xyz.sk1.minecraft.pxdemo.world.terrarin;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;
import xyz.sk1.minecraft.pxdemo.world.terrarin.noises.NoiseGenerator;

public class NoisedTerrainGenerator implements Generator {

    NoiseGenerator perlinNoiseGenerator;

    public NoisedTerrainGenerator(){
        this.perlinNoiseGenerator = new NoiseGenerator(12345L);
    }

    @Override
    public void generate(@NotNull GenerationUnit unit) {

        // Get the start and end coordinates of the generation area
        Point start = unit.absoluteStart();
        Point end = unit.absoluteEnd();

        int startX = (int) start.x();
        int startY = (int) start.y();
        int startZ = (int) start.z();
        int endX = (int) end.x();
        int endY = (int) end.y();
        int endZ = (int) end.z();

        // Noise parameters for smooth terrain generation
        double frequency = 0.01; // Frequency of the noise function
        double amplitude = 20;   // Amplitude of the noise function

        for (int x = startX; x < endX; x++) {
            for (int z = startZ; z < endZ; z++) {
                // Generate height using OpenSimplex noise
                int height = this.perlinNoiseGenerator.getHeightAt(x, z);

                // Set blocks up to the generated height
                for (int y = startY; y <= height; y++) {
                    unit.modifier().setBlock(x - startX, y, z - startZ, Block.STONE);
                }

                // Optionally, add a layer of grass on top
                if (height >= startY) {
                    unit.modifier().setBlock(x - startX, height, z - startZ, Block.GRASS_BLOCK);
                }
            }
        }

    }



}
