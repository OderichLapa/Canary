package com.abdelaziz.canary.mixin.block.hopper;

import com.abdelaziz.canary.common.hopper.UpdateReceiver;
import com.abdelaziz.canary.common.world.WorldHelper;
import com.abdelaziz.canary.common.world.blockentity.BlockEntityGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(Level.class)
public class LevelMixin {
    @Shadow
    @Final
    private static Direction[] DIRECTIONS;

    @Inject(
            method = "markAndNotifyBlock",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;onBlockStateChange(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD

    )
    private void updateHopperOnUpdateSuppression(BlockPos pos, LevelChunk worldChunk, BlockState state, BlockState blockState, int flags, int maxUpdateDepth, CallbackInfo ci, Block block, BlockState blockState2) {
        if ((flags & Block.UPDATE_NEIGHBORS) == 0) {
            //No block updates were sent. We need to update nearby hoppers to avoid outdated inventory caches being used

            //Small performance improvement when getting block entities within the same chunk.
            Map<BlockPos, BlockEntity> blockEntities = WorldHelper.areNeighborsWithinSameChunk(pos) ? worldChunk.getBlockEntities() : null;
            if (blockState != blockState2 && (blockEntities == null || !blockEntities.isEmpty())) {
                for (Direction direction : DIRECTIONS) {
                    BlockPos offsetPos = pos.relative(direction);
                    //Directly get the block entity instead of getting the block state first. Maybe that is faster, maybe not.
                    BlockEntity hopper = blockEntities != null ? blockEntities.get(offsetPos) : ((BlockEntityGetter) this).getLoadedExistingBlockEntity(offsetPos);
                    if (hopper instanceof UpdateReceiver updateReceiver) {
                        updateReceiver.invalidateCacheOnNeighborUpdate(direction == Direction.DOWN);
                    }
                }
            }
        }
    }
}