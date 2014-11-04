package com.arc.bloodarsenal.items.bauble;

import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemBauble extends Item implements IBauble
{
    public ItemBauble()
    {
        super();
        setMaxStackSize(1);
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);

        for (int i = 0; i < baubles.getSizeInventory(); i++)
        {
            if (baubles.isItemValidForSlot(i, par1ItemStack))
            {
                ItemStack stackInSlot = baubles.getStackInSlot(i);

                if (stackInSlot == null || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, par3EntityPlayer))
                {
                    if (!par2World.isRemote)
                    {
                        baubles.setInventorySlotContents(i, par1ItemStack.copy());

                        if (!par3EntityPlayer.capabilities.isCreativeMode)
                        {
                            par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                        }
                    }

                    onEquipped(par1ItemStack, par3EntityPlayer);

                    if(stackInSlot != null)
                    {
                        ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, par3EntityPlayer);
                        return stackInSlot.copy();
                    }
                    break;
                }
            }
        }

        return par1ItemStack;
    }

    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player)
    {
        if(player.ticksExisted == 1)
        {
            onEquippedOrLoadedIntoWorld(stack, player);
        }
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player)
    {
        onEquippedOrLoadedIntoWorld(stack, player);
    }

    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player)
    {
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player)
    {
    }
}