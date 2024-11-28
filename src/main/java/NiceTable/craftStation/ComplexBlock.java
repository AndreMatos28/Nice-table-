package NiceTable.craftStation;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ComplexBlock extends Block implements EntityBlock {

    public ComplexBlock() {
        // Faça com que nosso bloco se comporte como um bloco de metal
        super(BlockBehaviour.Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
    }

    // Nosso bloco tem uma entidade de bloco associada. Este método da interface EntityBlock é usado para criar essa entidade de bloco
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ComplexBlockEntity(pos, state);
    }

    // Este método é usado para criar um BlockEntityTicker para nossa entidade de bloco. Este ticker pode ser usado para realizar certas ações a cada tick
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            // Não temos nada para fazer no lado do cliente
            return null;
        } else {
            // No lado do servidor, delegamos o tick para nossa entidade de bloco
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof ComplexBlockEntity be) {
                    be.tickServer();
                }
            };
        }
    }
}