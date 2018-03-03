package me.NinetyNine.staffmode.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaffCommands implements CommandExecutor, Listener {

	// Added some code NinetyNine 3/3/18

	private ArrayList<Player> vanished = new ArrayList<Player>();
	private ArrayList<Player> staff = new ArrayList<Player>();
	public HashMap<Player, ItemStack[]> itemhash = new HashMap<Player, ItemStack[]>();

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = (Player) sender;
		Inventory inv = player.getInventory();
		PotionEffect res = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0, false);

		ItemStack[] playerinv = player.getInventory().getContents();

		if (cmd.getName().equalsIgnoreCase("staff")) {
			if (!(sender instanceof Player)) {
				player.sendMessage(ChatColor.AQUA + "[StaffMode]" + ChatColor.RED + "You\'re not a player!");
				return false;
			} else if (player.hasPermission("staffmode.use")) {
				if (!this.staff.contains(player)) {
					itemhash.put(player, playerinv);
					staff.add(player);

					player.setGameMode(GameMode.CREATIVE);
					player.getInventory().clear();
					ItemStack blazerod = new ItemStack(Material.BLAZE_ROD, 1);
					ItemMeta rodmeta = blazerod.getItemMeta();
					rodmeta.setDisplayName("§9Random Teleporter");
					blazerod.setItemMeta(rodmeta);
					inv.setItem(1, blazerod);
					ItemStack we = new ItemStack(Material.WOOD_AXE, 1);
					ItemMeta wemeta = we.getItemMeta();
					wemeta.setDisplayName("§5WorldEdit Wand");
					we.setItemMeta(wemeta);
					inv.setItem(2, we);
					ItemStack hide = new ItemStack(Material.STONE_BUTTON, 1);
					ItemMeta hidemeta = hide.getItemMeta();
					hidemeta.setDisplayName("§7Hide your arms");
					hide.setItemMeta(hidemeta);
					inv.setItem(3, hide);
					ItemStack feather = new ItemStack(Material.FEATHER, 1);
					ItemMeta feathermeta = feather.getItemMeta();
					feathermeta.setDisplayName("§aVanish");
					feathermeta.setLore(Arrays.asList("Vanish in thin air"));
					feather.setItemMeta(feathermeta);
					inv.setItem(5, feather);
					ItemStack magma = new ItemStack(Material.MAGMA_CREAM, 1);
					ItemMeta magmameta = magma.getItemMeta();
					magmameta.setDisplayName("§cGamemode Changer");
					magma.setItemMeta(magmameta);
					inv.setItem(6, magma);
					player.sendMessage("§1[§4StaffMode§1] §2You are now in §cStaff Mode§2!");
				} else {
					staff.remove(player);
					player.getInventory().clear();

					if (itemhash.containsKey(player)) {
						ItemStack[] items = itemhash.get(player);
						player.getInventory().setContents(items);
					}

					player.setGameMode(GameMode.SURVIVAL);
					player.addPotionEffect(res);
					player.sendMessage("§1[§4StaffMode§1] §cStaff Mode §2disabled!");
				}
			} else {
				player.sendMessage("§1[§4StaffMode§1] §l§cYou do not have permissions to use this!");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("chat")) {
			if (player.hasPermission("staffmode.clear")) {
				// edited, thx
				for (int i = 0; i < 100; i++) {
					Bukkit.getServer().broadcastMessage("\n" + ChatColor.DARK_RED
									+ "=============================================\n" + ChatColor.DARK_PURPLE
									+ "The server chat has been cleared by " + ChatColor.DARK_RED
									+ player.getPlayer().getName() + ChatColor.DARK_PURPLE + "!" + ChatColor.DARK_RED
									+ "=============================================\n");
				}
			} else {
				player.sendMessage("§1[§4StaffMode§1] §l§cYou do not have permissions to use this!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("vanish")) {
			if (player.hasPermission("staffmode.vanish")) {
				Player pl;
				Iterator<?> arg6;
				if (!this.vanished.contains(player)) {
					arg6 = Bukkit.getServer().getOnlinePlayers().iterator();

					while (arg6.hasNext()) {
						pl = (Player) arg6.next();
						pl.hidePlayer(player);
					}
					vanished.add(player);
					player.sendMessage(ChatColor.AQUA + "§1[§4StaffMode§1] §2" + ChatColor.GRAY
							+ "You have been vanished! By this, players won\'t be able to see you!");
				} else {
					arg6 = Bukkit.getServer().getOnlinePlayers().iterator();

					while (arg6.hasNext()) {
						pl = (Player) arg6.next();
						pl.showPlayer(player);
					}

					vanished.remove(player);
					player.sendMessage(ChatColor.AQUA + "§1[§4StaffMode§1] §2" + ChatColor.GRAY
							+ "You have been unvanished! Players can now see you!");
				}
			} else {
				player.sendMessage("§1[§4StaffMode§1] §4You do not have permissions to execute this command!");
			}
			return true;
		}
		return true;
	}
}