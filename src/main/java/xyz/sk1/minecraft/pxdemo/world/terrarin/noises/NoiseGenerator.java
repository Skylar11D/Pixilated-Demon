package xyz.sk1.minecraft.pxdemo.world.terrarin.noises;

public class NoiseGenerator {
    public OpenSimplex2S getNoise() {
        return noise;
    }

    private final OpenSimplex2S noise;
    private long seed;

    public NoiseGenerator(long seed) {
        // Initialize Perlin noise with a seed for consistent results
        noise = new OpenSimplex2S();
        this.seed = seed;
    }

    // Generate height based on 2D Perlin noise
    public int getHeightAt(int x, int z) {
//        double frequency = 0.01; // Controls the scale of the noise
//        double noiseValue = noise.noise2(111, x * frequency, z * frequency);
//        return (int) (noiseValue * 30 + 64); // Adjust scaling and base level as needed
        // Calculate the noise value and height
        double frequency = 0.006; // Frequency of the noise function
        double amplitude = 16;   // Amplitude of the noise function

        double noiseValue = this.noise.noise2(seed, x*frequency, z*frequency);
        int height = (int) (noiseValue * amplitude + 64); // Scale and offset
        return height;
    }
}
