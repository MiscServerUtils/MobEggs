package big_xplosion.mobeggs.event;

import big_xplosion.msu.core.helper.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.complex.ComplexLiving;
import org.spongepowered.api.entity.living.golem.Golem;
import org.spongepowered.api.entity.living.monster.Giant;
import org.spongepowered.api.entity.living.monster.Wither;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

//TODO: Return when Sponge(Vanilla/Forge) implements inventory handling
public class ListenerPlayerInteractEntity {

	@Listener
	public void onInteract(InteractEntityEvent.Secondary event) {
		Player player = event.getCause().first(Player.class).get();
		Entity target = event.getTargetEntity();

		if (!(target instanceof Living) || target instanceof Player || target instanceof Golem || target instanceof ComplexLiving || target instanceof Wither || target instanceof Giant)
			return;

		//HACK
		EntityPlayer mcPlayer = (EntityPlayer) player;

		if (mcPlayer.getHeldItem() == null || !mcPlayer.getHeldItem().getItem().equals(Items.redstone))
			return;

		if (!mcPlayer.inventory.hasItem(Items.egg))
			return;

		int slot = 0;
		for (int i = 0; i < mcPlayer.inventory.mainInventory.length; i++) {
			if (mcPlayer.inventory.mainInventory[i] != null && mcPlayer.inventory.mainInventory[i].getItem() == Items.egg) {
				slot = i;
				break;
			}
		}

		if (mcPlayer.getHeldItem().stackSize > 5)
			mcPlayer.inventory.setInventorySlotContents(mcPlayer.inventory.currentItem, new net.minecraft.item.ItemStack(Items.redstone, mcPlayer.getHeldItem().stackSize - 5));
		else
			mcPlayer.inventory.setInventorySlotContents(mcPlayer.inventory.currentItem, null);

		if (mcPlayer.inventory.getStackInSlot(slot).stackSize > 1)
			mcPlayer.inventory.setInventorySlotContents(slot, new net.minecraft.item.ItemStack(Items.egg, mcPlayer.inventory.getStackInSlot(slot).stackSize - 1));
		else
			mcPlayer.inventory.setInventorySlotContents(slot, null);
		//END-HACK

		/*if (!player.getItemInHand().isPresent())
			return;

		ItemStack item = player.getItemInHand().get();
		System.out.println("item in hand: " + item);
		if (!item.getItem().equals(ItemTypes.REDSTONE))
			return;

		System.out.println("item in hand: OK");

		Slot slot = player.getInventory().<Slot>query(ItemTypes.EGG);
		System.out.println("slot: " + slot);
		if (slot.isEmpty())
			return;

		System.out.println("slot: OK");

		if (item.getQuantity() > 5)
			player.setItemInHand(ItemStack.of(ItemTypes.REDSTONE, item.getQuantity() - 5));
		else
			player.setItemInHand(null);

		if (slot.getStackSize() > 1)
			slot.set(ItemStack.of(ItemTypes.EGG, slot.getStackSize() - 1));
		else
			slot.set(null);*/

		ItemStack egg = ItemStack.of(ItemTypes.SPAWN_EGG, 1);
		egg.offer(Keys.SPAWNABLE_ENTITY_TYPE, target.getType());
		ItemHelper.dropItemOnPlayer(egg, player);
		target.remove();
	}
}