package me.NinetyNine.staffmode;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffEventHandlers implements Listener {

	// Edited by NinetyNine 3/3/18.
	
	@EventHandler
	public void RandomTP(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;

		if (p.getItemInHand().getType() == Material.BLAZE_ROD)
			onRandomTP(p);
	}

	public void onRandomTP(Player player) {
		Random r = new Random();

		int target = r.nextInt(Bukkit.getServer().getOnlinePlayers().size());
		String pn = player.getPlayer().getName();

		ArrayList<Player> players = new ArrayList<Player>();
		for (Player e1 : Bukkit.getOnlinePlayers())
			players.add(e1);
		Player randomPlayer = players.get(new Random().nextInt(players.size()));
		player.teleport(randomPlayer.getLocation());
		player.sendMessage(
				"§1[§4StaffMode§1] §2You have been teleported to " + target + " §8(§l§7" + pn + "§8)" + "§2!");
	}

	@EventHandler
	public void onRight(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;

		if (player.getItemInHand().getType().equals(Material.FEATHER) && player.getItemInHand().hasItemMeta()
				&& player.getItemInHand().getItemMeta().getDisplayName().equals("§aVanish")) {
			player.getItemInHand().getItemMeta().addEnchant(Enchantment.ARROW_INFINITE, 1, false);
			player.performCommand("vanish");
			return;
		}
	}

	@EventHandler
	public void onVanished(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;

		if (player.getItemInHand().getType().equals(Material.FEATHER) && player.getItemInHand().hasItemMeta()
				&& player.getItemInHand().getItemMeta().getDisplayName().equals("§aVanish")) {
			player.getItemInHand().getItemMeta().removeEnchant(Enchantment.ARROW_INFINITE);
			return;
		}
	}

	@EventHandler
	public void onPunch(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;

		if (player.getItemInHand().getType().equals(Material.MAGMA_CREAM)) {
			openGUI(player);
			player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 10, 10);
			return;
		}
	}

	private void openGUI(Player p1) {
		Inventory in = Bukkit.createInventory(null, 9, ChatColor.RED + "Choose a gamemode!");

		ItemStack survival = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta meta1 = survival.getItemMeta();
		meta1.setDisplayName("§cSURVIVAL");
		survival.setItemMeta(meta1);
		ItemStack creative = new ItemStack(Material.COMMAND);
		ItemMeta meta2 = creative.getItemMeta();
		meta2.setDisplayName("§9CREATIVE");
		creative.setItemMeta(meta2);
		ItemStack adventure = new ItemStack(Material.DIRT);
		ItemMeta meta3 = adventure.getItemMeta();
		meta3.setDisplayName("§aADVENTURE");
		adventure.setItemMeta(meta3);

		in.setItem(2, survival);
		in.setItem(4, creative);
		in.setItem(6, adventure);

		p1.openInventory(in);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		ItemStack item = event.getCurrentItem();

		if (event.getWhoClicked() instanceof Player) {
			if (event.getInventory().getTitle().equals("§cChoose a gamemode!")) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().getDisplayName().equals("§9CREATIVE")) {
						if (player.hasPermission("staffmode.gamemode.creative")) {
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage("§1[§4StaffMode§1] §6Gamemode updated to §aCreative!");
							player.closeInventory();
						} else {
							player.closeInventory();
							player.sendMessage("§1[§4StaffMode§1] §cNo permissions.");
						}
					}
					if (item.getItemMeta().getDisplayName().equals("§cSURVIVAL")) {
						if (player.hasPermission("staffmode.gamemode.survival")) {
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage("§1[§4StaffMode§1] §6Gamemode updated to §cSurvival!");
							player.closeInventory();
						} else {
							player.closeInventory();
							player.sendMessage("§1[§4StaffMode§1] §cNo permissions.");
						}
					}
					if (item.getItemMeta().getDisplayName().equals("§aADVENTURE")) {
						if (player.hasPermission("staffmode.gamemode.adventure")) {
							player.setGameMode(GameMode.ADVENTURE);
							player.sendMessage("§1[§4StaffMode§1] §6Gamemode updated to §2Adventure!");
							player.closeInventory();
						} else {
							player.closeInventory();
							player.sendMessage("§1[§4StaffMode§1] §cNo permissions.");
						}
					}
				}
			}
		}
	}
}