package com.SirBlobman.enderpearl.cooldown.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class PearlPatchListener implements Listener
{
    private ImmutableSet<Material> blockedPearlTypes;
    private JavaPlugin plugin;
    
    public PearlPatchListener(final JavaPlugin plugin) {
        this.blockedPearlTypes = Sets.immutableEnumSet(Material.THIN_GLASS, new Material[] {
        		Material.IRON_FENCE, 
        		Material.FENCE, 
        		Material.NETHER_FENCE, 
        		Material.FENCE_GATE, 
        		Material.ACACIA_STAIRS, 
        		Material.BIRCH_WOOD_STAIRS, 
        		Material.BRICK_STAIRS, 
        		Material.COBBLESTONE_STAIRS, 
        		Material.DARK_OAK_STAIRS, 
        		Material.JUNGLE_WOOD_STAIRS, 
        		Material.NETHER_BRICK_STAIRS, 
        		Material.QUARTZ_STAIRS, 
        		Material.SANDSTONE_STAIRS, 
        		Material.SMOOTH_STAIRS, 
        		Material.SPRUCE_WOOD_STAIRS, 
        		Material.WOOD_STAIRS });
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL) {
            final Block block = event.getClickedBlock();
            if (block.getType().isSolid() && !(block.getState() instanceof InventoryHolder)) {
                event.setCancelled(true);
                final Player player = event.getPlayer();
                player.setItemInHand(event.getItem());
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPearlClip(final PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            final Location to = event.getTo();
            if (blockedPearlTypes.contains(to.getBlock().getType())) {
                event.setCancelled(true);
            }
            to.setX(to.getBlockX() + 0.5);
            to.setZ(to.getBlockZ() + 0.5);
            event.setTo(to);
        }
    }
}
