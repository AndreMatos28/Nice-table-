package NiceTable.craftStation;


// Importa as classes necessárias
//

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


//// Define a classe da tabela de craft
//public class CraftTable extends Block implements EntityBlock {
//
//    @Override
//    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
//        return null;
//    }
//}

// O valor aqui deve corresponder a uma entrada no arquivo META-INF/mods.toml
@Mod(CraftTable.MODID)
public class CraftTable {

    // Defina o id do mod em um local comum para que tudo possa se referenciar
    public static final String MODID = "crafttable";
    // Referencie diretamente um logger slf4j
    private static final Logger LOGGER = LogUtils.getLogger();

    // Crie um Deferred Register para armazenar Blocos que serão registrados sob o namespace "crafttable"
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Crie um Deferred Register para armazenar Itens que serão registrados sob o namespace "crafttable"
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Crie um novo Bloco com o id "crafttable:example_block", combinando o namespace e o caminho
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    // Crie um novo BlockItem com o id "crafttable:example_block", combinando o namespace e o caminho
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () ->
            new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public CraftTable() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registre o método commonSetup para carregamento do mod
        modEventBus.addListener(this::commonSetup);

        // Registre os Deferred Registers no mod event bus para que blocos e itens sejam registrados
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        // Registre-se para eventos do servidor e outros eventos de jogo nos quais estamos interessados
        MinecraftForge.EVENT_BUS.register(this);

        // Registre o item em uma guia criativa
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Algum código de configuração comum
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // Você pode usar SubscribeEvent e deixar que o Event Bus descubra métodos para chamar
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Faça algo quando o servidor iniciar
        LOGGER.info("HELLO from server starting");
    }

    // Você pode usar EventBusSubscriber para registrar automaticamente todos os métodos estáticos na classe anotados com @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Algum código de configuração do cliente
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}