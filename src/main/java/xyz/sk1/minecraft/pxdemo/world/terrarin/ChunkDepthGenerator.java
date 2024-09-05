package xyz.sk1.minecraft.pxdemo.world.terrarin;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import net.minestom.server.instance.generator.UnitModifier;

import java.util.Random;
import java.util.logging.Logger;

public class ChunkDepthGenerator implements Generator {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(ChunkDepthGenerator.class.getName());

    @Override
    public void generate(GenerationUnit unit) {
        UnitModifier modifier = unit.modifier();
        Point start = unit.absoluteStart();
        Point end = unit.absoluteEnd();

        // Log chunk boundaries
        LOGGER.info("Chunk boundaries: Start " + start + ", End " + end);

        // Generate bedrock layer at Y=-63 to Y=-62
        modifier.fillHeight(-63, -62, Block.BEDROCK);
        // Generate blackstone layer at Y=-62 to Y=-5
        modifier.fillHeight(-62, 1, Block.BLACKSTONE);
        // Generate deepslate layer  from Y=-5 to Y=50
        modifier.fillHeight(1, 20, Block.DEEPSLATE);
        // Generate stone layer from Y=-5 to Y=60
        modifier.fillHeight(20, 60, Block.STONE);

        // Generate surface layer from Y=60 to Y=65
        modifier.fillHeight(60, 64, Block.DIRT);
        modifier.fillHeight(64, 65, Block.GRASS_BLOCK);

        final int diamondOreMinY = -64;
        final int diamondOreMaxY = 1;

        final int coalOreMinY = 10;
        final int coalOreMaxY = 50;

        // Coal ore generation between Y=20 and Y=37 with 1% chance per chunk section
        for (int y = coalOreMinY; y <= coalOreMaxY; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.01) {
                            try {
                                modifier.setBlock(x, y, z, Block.COAL_ORE);
                                modifier.setBlock(x-1, y, z, Block.COAL_ORE);
                                modifier.setBlock(x-2, y, z, Block.COAL_ORE);
                                modifier.setBlock(x-1, y-1, z, Block.COAL_ORE);
                                modifier.setBlock(x-2, y+2, z, Block.COAL_ORE);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        // Diamond ore generation between Y=-20 and Y=-60 with 0.01% chance per block
        for (int y = diamondOreMinY; y <= diamondOreMaxY; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.002) {
                            try {
                                modifier.setBlock(x, y, z, Block.DEEPSLATE_DIAMOND_ORE);
                                modifier.setBlock(x-1, y, z, Block.DEEPSLATE_DIAMOND_ORE);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        for (int y = 20; y <= 25; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.40) {
                            try {
                                modifier.setBlock(x, y, z, Block.DEEPSLATE);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        for (int y = -10; y <= 1; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.40) {
                            try {
                                modifier.setBlock(x, y, z, Block.DEEPSLATE);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        for (int y = -62; y <= -61; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.54) {
                            try {
                                modifier.setBlock(x, y, z, Block.BEDROCK);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        for (int y = 55; y <= 63; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.40) {
                            try {
                                modifier.setBlock(x, y, z, Block.COARSE_DIRT);
                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

        for (int y = 65; y <= 65; y++) {
            for (int x = start.blockX(); x <= end.blockX(); x++) {
                for (int z = start.blockZ(); z <= end.blockZ(); z++) {

                    // Check if coordinates are within the chunk's boundaries
                    if (x >= start.blockX() && x <= end.blockX() &&
                            y >= start.blockY() && y <= end.blockY() &&
                            z >= start.blockZ() && z <= end.blockZ()) {
                        if (RANDOM.nextDouble() < 0.15) {
                            try {
                                modifier.setBlock(x, y, z, Block.SHORT_GRASS);

                            } catch (IllegalArgumentException e) {
                                LOGGER.severe("Exception setting block at: x=" + x + ", y=" + y + ", z=" + z + " - " + e.getMessage());
                            }
                        }
                    } else {
                        // Log if coordinates are out of bounds
                        LOGGER.warning("Coordinates out of bounds: x=" + x + ", y=" + y + ", z=" + z);
                    }
                }
            }
        }

    }

}
