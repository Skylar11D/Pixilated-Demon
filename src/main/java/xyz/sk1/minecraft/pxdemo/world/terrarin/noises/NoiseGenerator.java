package xyz.sk1.minecraft.pxdemo.world.terrarin.noises;

public class NoiseGenerator {
    private OpenSimplex2S noise;

    public NoiseGenerator(long seed) {
        // Initialize Perlin noise with a seed for consistent results
        noise = new OpenSimplex2S();
    }

    // Generate height based on 2D Perlin noise
    public int getHeightAt(int x, int z) {
        double frequency = 0.01; // Controls the scale of the noise
        double noiseValue = noise.noise2(111, x * frequency, z * frequency);
        return (int) (noiseValue * 30 + 64); // Adjust scaling and base level as needed
    }
}
