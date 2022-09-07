package com.abdelaziz.canary.common.hopper;

public class HopperCachingState {

    public enum BlockInventory {
        UNKNOWN, // No information cached
        BLOCK_STATE, // Known to be Composter-like inventory (inventory from block, but not block entity, only depends on block state)
        BLOCK_ENTITY, // Known to be BlockEntity inventory
        NO_BLOCK_INVENTORY // Known to be a block without hopper interaction (-> interact with entities instead)
    }
}
