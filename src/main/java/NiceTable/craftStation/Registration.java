//package NiceTable.craftStation;
//
//// Importa as classes necessárias
//
//import net.minecraft.world.inventory.MenuType;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//
//// Define o mod
//@Mod("CraftStation")
//public class ModInitializer {
//
//    // Define o registro de menus
//    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
//            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "CraftStation");
//
//    // Inicializa o mod
//    public ModInitializer() {
//
//        // Registra o menu de craft personalizado
//        MENU_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
//    }
//}


package NiceTable.craftStation;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


@Mod("CraftStation")
public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CraftTable.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CraftTable.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CraftTable.MODID);

    public static final RegistryObject<SimpleBlock> SIMPLE_BLOCK = BLOCKS.register("simple_block", SimpleBlock::new);
    public static final RegistryObject<Item> SIMPLE_BLOCK_ITEM = ITEMS.register("simple_block", () ->
            new BlockItem(SIMPLE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<ComplexBlock> COMPLEX_BLOCK = BLOCKS.register("complex_block", ComplexBlock::new);
    public static final RegistryObject<Item> COMPLEX_BLOCK_ITEM = ITEMS.register("complex_block", () ->
            new BlockItem(COMPLEX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<ComplexBlockEntity>> COMPLEX_BLOCK_ENTITY = BLOCK_ENTITIES.register("complex_block",
            () -> BlockEntityType.Builder.of(ComplexBlockEntity::new, COMPLEX_BLOCK.get()).build(null));

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }

    static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(SIMPLE_BLOCK_ITEM);
            event.accept(COMPLEX_BLOCK_ITEM);
        }
    }
}
