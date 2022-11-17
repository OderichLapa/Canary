package com.abdelaziz.canary.mixin.world.block_entity_ticking.sleeping;

import com.abdelaziz.canary.common.block.entity.SleepingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

    @Inject(
            /**
             * For Developers :
             *
             * - When you use the mod in the dev environment, you should use the regular names
             * use lambda$updateBlockEntityTicker$7 instead of m_187960_
             *
             * - When you are want to build this project use the SRG method names
             * use m_187960_ instead of lambda$updateBlockEntityTicker$7.
             *
             * You should also do that with common.entity.EntityClassGroup
             * */
            method = "lambda$updateBlockEntityTicker$7", //m_187960_ - lambda$updateBlockEntityTicker$7
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addBlockEntityTicker(Lnet/minecraft/world/level/block/entity/TickingBlockEntity;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void setBlockEntityTickingOrder(BlockEntity blockEntity, BlockEntityTicker<?> blockEntityTicker, BlockPos pos, @Coerce Object wrappedBlockEntityTickInvoker, CallbackInfoReturnable<?> cir, TickingBlockEntity blockEntityTickInvoker, @Coerce Object wrappedBlockEntityTickInvoker2) {
        if (blockEntity instanceof SleepingBlockEntity sleepingBlockEntity) {
            sleepingBlockEntity.setTickWrapper((RebindableTickingBlockEntityWrapperAccessor) wrappedBlockEntityTickInvoker2);
        }
    }

    @Inject(
            /**
             * For Developers :
             *
             * - When you use the mod in the dev environment, you should use the regular names
             * use lambda$updateBlockEntityTicker$7 instead of m_187960_
             *
             * - When you are want to build this project use the SRG names
             * use m_187960_ instead of lambda$updateBlockEntityTicker$7.
             *
             * You should also do that with common.entity.EntityClassGroup
             * */
            method = "lambda$updateBlockEntityTicker$7", //m_187960_ - lambda$updateBlockEntityTicker$7
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk$RebindableTickingBlockEntityWrapper;rebind(Lnet/minecraft/world/level/block/entity/TickingBlockEntity;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void setBlockEntityTickingOrder(BlockEntity blockEntity, BlockEntityTicker<?> blockEntityTicker, BlockPos pos, @Coerce Object wrappedBlockEntityTickInvoker, CallbackInfoReturnable<?> cir, TickingBlockEntity blockEntityTickInvoker) {
        if (blockEntity instanceof SleepingBlockEntity sleepingBlockEntity) {
            sleepingBlockEntity.setTickWrapper((RebindableTickingBlockEntityWrapperAccessor) wrappedBlockEntityTickInvoker);
        }
    }
}