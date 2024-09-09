package xyz.sk1.minecraft.pxdemo.world.terrarin;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import xyz.sk1.minecraft.pxdemo.world.terrarin.noises.NoiseGenerator;
import xyz.sk1.minecraft.pxdemo.world.terrarin.noises.OpenSimplex2S;

import java.util.Random;

public class NoisedTerrainGenerator implements Generator {

    private final NoiseGenerator noiseGenerator;
    private final OpenSimplex2S openSimplex2S;
    private final int width;
    private final int height;
    private final int maxTerrainHeight;
    private final Random random;

    public NoisedTerrainGenerator() {
        this.openSimplex2S = new OpenSimplex2S();
        this.noiseGenerator = new NoiseGenerator(new Random().nextInt());
        this.width = 16;
        this.height = 16;
        this.maxTerrainHeight = 64;
        this.random = new Random();
    }

    public void generate(GenerationUnit unit) {
        int chunkX = unit.absoluteStart().chunkX();
        int chunkZ = unit.absoluteEnd().chunkZ();

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                int blockX = chunkX * 16 + x;
                int blockZ = chunkZ * 16 + z;

                // Generate noise value
                double noiseValue = openSimplex2S.noise2(14, blockX / 16.0, blockZ / 16.0);

                // Normalize and scale the noise
                noiseValue = (noiseValue + 1) / 2; // Normalize to 0-1
                noiseValue *= maxTerrainHeight;

                // Place blocks based on noise value
                int blockY = (int) noiseValue;
                for (int y = 0; y < blockY; y++) {
                    unit.modifier().setBlock(new Pos(blockX, y, blockZ), Block.STONE);
                }
            }
        }
    }

    public void fillChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(new Pos(chunk.getChunkX() * 16 + x, y, chunk.getChunkZ() * 16 + z), Block.AIR);
                }
            }
        }
    }



}
