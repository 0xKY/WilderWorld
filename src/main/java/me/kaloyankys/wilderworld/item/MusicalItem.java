package me.kaloyankys.wilderworld.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MusicalItem extends Item {
    private final int trackLength;
    private final SoundEvent instrument;

    public MusicalItem(Settings settings, int trackLength, SoundEvent instrument) {
        super(settings);
        this.trackLength = trackLength;
        this.instrument = instrument;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        boolean sneaking = user.isSneaking();
        float playerPitch = user.getPitch();
        if (trackLength < 10) {
            int root = user.getInventory().selectedSlot;
            int third = root + 4;
            int fifth = third + 3;
            if (!sneaking) {
                world.playSound(user, user.getBlockPos(), instrument, SoundCategory.PLAYERS, 16.0f,
                        (float) Math.pow(2.0, (root - 12) / 12.0));
            } else {
                if (playerPitch == 90) {
                    third -= 1;
                } else if (playerPitch == -90) {
                    fifth += 1;
                }
                world.playSound(user, user.getBlockPos(), instrument, SoundCategory.PLAYERS, 16.0f,
                        (float) Math.pow(2.0, (root - 12) / 12.0));
                world.playSound(user, user.getBlockPos(), instrument, SoundCategory.PLAYERS, 16.0f,
                        (float) Math.pow(2.0, (third - 12) / 12.0));
                world.playSound(user, user.getBlockPos(), instrument, SoundCategory.PLAYERS, 16.0f,
                        (float) Math.pow(2.0, (fifth - 12) / 12.0));
            }
        } else {
            world.playSound(user, user.getBlockPos(), instrument, SoundCategory.PLAYERS, 16.0f, 1.0f);
        }
        return super.use(world, user, hand);
    }
}
