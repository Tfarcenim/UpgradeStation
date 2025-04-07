package tfar.upgradestation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UpgradeStationBlock extends Block {


    public static final VoxelShape SHAPE_BASE = Block.box(1, 0, 1, 15, 3, 15);
    public static final VoxelShape SHAPE_POST = Block.box(3, 2, 3, 13, 14, 13);
    public static final VoxelShape SHAPE_COMMON = Shapes.or(SHAPE_BASE, SHAPE_POST);
    public static final VoxelShape SHAPE_TOP_PLATE = Block.box(0, 15, 0, 16, 16, 16);
    public static final VoxelShape SHAPE_COLLISION = Shapes.or(SHAPE_COMMON, SHAPE_TOP_PLATE);
    public static final VoxelShape SHAPE_WEST = SHAPE_COLLISION;
    public static final VoxelShape SHAPE_NORTH = SHAPE_COLLISION;
    public static final VoxelShape SHAPE_EAST = SHAPE_COLLISION;
    public static final VoxelShape SHAPE_SOUTH = SHAPE_COLLISION;


    public UpgradeStationBlock(Properties $$0) {
        super($$0);
        this.registerDefaultState(this.stateDefinition.any().setValue(LecternBlock.FACING, Direction.NORTH));
    }

    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    @Override
    public RenderShape getRenderShape(BlockState $$0) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
        return SHAPE_COMMON;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            //player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return this.defaultBlockState().setValue(LecternBlock.FACING, $$0.getHorizontalDirection().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState $$0, Rotation $$1) {
        return $$0.setValue(LecternBlock.FACING, $$1.rotate($$0.getValue(LecternBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState $$0, Mirror $$1) {
        return $$0.rotate($$1.getRotation($$0.getValue(LecternBlock.FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        switch ($$0.getValue(LecternBlock.FACING)) {
            case NORTH -> {
                return SHAPE_NORTH;
            }
            case SOUTH -> {
                return SHAPE_SOUTH;
            }
            case EAST -> {
                return SHAPE_EAST;
            }
            case WEST -> {
                return SHAPE_WEST;
            }
            default -> {
                return SHAPE_COMMON;
            }
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
        return new SimpleMenuProvider(($$2x, $$3, $$4) -> {
            return new UpgradeStationMenu($$2x, $$3, ContainerLevelAccess.create($$1, $$2));
        }, CONTAINER_TITLE);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(LecternBlock.FACING);
    }

}
