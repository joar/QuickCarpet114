package quickcarpet.mixin.tickSpeed;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import quickcarpet.QuickCarpet;
import quickcarpet.annotation.Feature;
import quickcarpet.utils.Reflection;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Feature("tickSpeed")
@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
    @Shadow @Final private List<ServerPlayerEntity> players;

    @Shadow public abstract ServerChunkManager getChunkManager();

    protected ServerWorldMixin(MutableWorldProperties mutableWorldProperties, DimensionType dimensionType, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l) {
        super(mutableWorldProperties, dimensionType, supplier, bl, bl2, l);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tickFreeze(BooleanSupplier shouldContinueTicking, CallbackInfo ci) {
        if (QuickCarpet.getInstance().tickSpeed.isPaused()) {
            for (ServerPlayerEntity p : this.players) p.tick();
            Reflection.tickChunkManager(this.getChunkManager(), shouldContinueTicking);
            ci.cancel();
        }
    }
}
