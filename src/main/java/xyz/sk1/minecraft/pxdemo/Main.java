package xyz.sk1.minecraft.pxdemo;

import com.sun.source.util.Plugin;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.UnitModifier;
import net.minestom.server.network.packet.server.play.ExplosionPacket;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.world.DimensionType;
import xyz.sk1.minecraft.pxdemo.world.terrarin.ChunkDepthGenerator;
import xyz.sk1.minecraft.pxdemo.world.terrarin.NoisedTerrainGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Main {

    private final static Logger log = Logger.getLogger("Pixilated Demon");

    private static InstanceContainer instanceContainer;
    private static Map<Class<?>, Consumer<?>> eventListeners = new HashMap<>();

    public static void main(String[] args) {

        MinecraftServer server = MinecraftServer.init();
        instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer(DimensionType.OVERWORLD);
        ChunkDepthGenerator customChunkGenerator = new ChunkDepthGenerator();
        //NoisedTerrainGenerator noisedTerrainGenerator = new NoisedTerrainGenerator();
        //instanceContainer.setGenerator(Main::basicTerrainsGenerator);
        instanceContainer.setGenerator(customChunkGenerator::generate); //terrains generation starts from here
        instanceContainer.setChunkSupplier(LightingChunk::new); //provides light

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        log.info("Initializing Events Handlers..");
        initEventsListeners(globalEventHandler);

        log.info("Registering Events Listeners..");
        for(Map.Entry entry : eventListeners.entrySet()){
            globalEventHandler.addListener(((Class) entry.getKey()), ((Consumer) entry.getValue()));
        }

        MojangAuth.init(); //making it only for mojang authenticated players (not-cracked)
        server.start("127.0.0.1", 11666);

    }

    private static void basicTerrainsGenerator(GenerationUnit generationUnit){
        if(generationUnit == null)
            throw new IllegalArgumentException("Geneartion unit cannot be null");

        log.info("Establishing world generation..");

        int minCoalHeight = 10;
        int maxCoalHeight = 20;
        int maxCoalVeinWidth = 4;

        UnitModifier modifier = generationUnit.modifier();

        modifier.fillHeight(0, 1, Block.BEDROCK);

        for (int height = 1; height < 37; height++) {
            double random = Math.random();

            if (random < 0.10) {
                modifier.fillHeight(height,height+1, Block.COAL_ORE);
                continue;
            }

            if (random < 0.05) {
                modifier.fillHeight(height, height + 1, Block.DIAMOND_ORE);
                continue;
            }

            modifier.fillHeight(height, height + 1, Block.STONE);

        }

        for (int i = 37; i < 46; i++) {
            modifier.fillHeight(i, i + 1, Block.STONE);
        }

        modifier.fillHeight(45, 50, Block.SANDSTONE);


    }

    private static void playerConfigEventHandler(AsyncPlayerConfigurationEvent event){
        Player player = event.getPlayer();

        event.setSpawningInstance(instanceContainer);

        player.setRespawnPoint(new Pos(0, 65, 0));
        player.setGameMode(GameMode.CREATIVE);
    }

    private static void playerInteractEventHandler(PlayerBlockInteractEvent event){
        if(event.getBlock().isAir())
            return;

        createExplosion(Pos.fromPoint(event.getBlockPosition()), instanceContainer);

    }

    private static void initEventsListeners(GlobalEventHandler eventsManager){
        eventListeners.put(
                AsyncPlayerConfigurationEvent.class,
                (Consumer< AsyncPlayerConfigurationEvent>) Main::playerConfigEventHandler
        );
        eventListeners.put(PlayerBlockInteractEvent.class, (Consumer<PlayerBlockInteractEvent>) Main::playerInteractEventHandler);
    }

    public static void createExplosion(Pos position, Instance instance) {
        float radius = 6.0f;  // The explosion radius
        byte[] records = new byte[0];  // No blocks affected
        float playerMotionX = 0.0f;
        float playerMotionY = 0.0f;
        float playerMotionZ = 0.0f;
        ExplosionPacket.BlockInteraction blockInteraction = ExplosionPacket.BlockInteraction.DESTROY; // Example interaction
        int smallParticleId = 0; // Placeholder
        byte[] smallParticleData = new byte[0]; // No small particle data
        int largeParticleId = 0; // Placeholder
        byte[] largeParticleData = new byte[0]; // No large particle data
        SoundEvent sound = SoundEvent.ENTITY_GENERIC_EXPLODE; // Example sound event

        // Create an explosion packet
        ExplosionPacket explosionPacket = new ExplosionPacket(
                position.x(),
                position.y(),
                position.z(),
                radius,
                records,
                playerMotionX,
                playerMotionY,
                playerMotionZ,
                blockInteraction,
                smallParticleId,
                smallParticleData,
                largeParticleId,
                largeParticleData,
                sound
        );

        // Send the packet to all players in the instance
        for (Player player : instance.getPlayers()) {
            player.sendPacket(explosionPacket);
        }
    }

}
