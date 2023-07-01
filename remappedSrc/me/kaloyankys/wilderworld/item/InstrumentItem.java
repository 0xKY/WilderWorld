package me.kaloyankys.wilderworld.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class InstrumentItem extends Item {
    private final int trackLength;
    private final SoundEvent major;
    private final SoundEvent minor;

    public InstrumentItem(Settings settings, int trackLength, SoundEvent major, SoundEvent minor) {
        super(settings);
        this.trackLength = trackLength;
        this.major = major;
        this.minor = minor;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        boolean sneaking = user.isSneaking();
        float playerPitch = user.getPitch();
        if (trackLength < 10
            //&& this.major != null && this.minor != null
        ) {
            if (!sneaking) {
                world.playSound(user, user.getBlockPos(), major, SoundCategory.PLAYERS, 16.0f,
                        (float) Math.pow(2.0, (playerPitch - 12) / 12.0));
            } else {
                //world.playSound(user, user.getBlockPos(), minor, SoundCategory.PLAYERS, 16.0f,
                //        (float) Math.pow(2.0, (playerPitch - 12) / 12.0));
            }
        } else {
            world.playSound(user, user.getBlockPos(), major, SoundCategory.PLAYERS, 16.0f, 1.0f);
        }
        return super.use(world, user, hand);
    }
}
