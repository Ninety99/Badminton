package me.NinetyNine.badminton;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

public class Badminton implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		Player player = (Player) sender;
		Inventory inv = player.getInventory();

		if (cmd.getName().equalsIgnoreCase("badminton")) {
			if (player.hasPermission("badminton.use")) {
				ItemStack paddle = new ItemStack(Material.STICK, 1);
				ItemMeta paddlemeta = paddle.getItemMeta();
				paddlemeta.setDisplayName("§aRacket");
				ArrayList<String> paddlelore = new ArrayList<String>();
				paddlelore.add("§6Badminton racket");
				paddlelore.toString();
				paddlemeta.setLore(paddlelore);
				paddle.setItemMeta(paddlemeta);

				ItemStack ball = new ItemStack(Material.SNOW_BALL, 1);
				ItemMeta ballmeta = ball.getItemMeta();
				ballmeta.setDisplayName("§aBall");
				ArrayList<String> balllore = new ArrayList<String>();
				balllore.add("§6Badminton ball");
				balllore.toString();
				ballmeta.setLore(balllore);
				ball.setItemMeta(ballmeta);

				inv.addItem(paddle);
				inv.addItem(ball);

				player.sendMessage("§8[§6Badminton§8] §2You have obtained the badminton materials!");
			} else {
				player.sendMessage("§cAre you in the right mode?");
				return false;
			}
		}
		return true;
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		Item itemdrop = e.getItemDrop();
		ItemStack sball = new ItemStack(Material.SNOW_BALL);
		Item thrownBallItem = player.getWorld().dropItem(player.getLocation(), sball);

		if (player.getItemInHand().getType().equals(Material.SNOW_BALL) && player.getItemInHand().hasItemMeta()
				&& player.getItemInHand().getItemMeta().getDisplayName().equals("§aBall")) {
			e.setCancelled(true);
			if (itemdrop.getItemStack().equals(sball)) {
				thrownBallItem.setVelocity(player.getEyeLocation().getDirection());
			}
		}
	}

	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		Player player = (Player) e.getEntity();
		EntityType eplayer = EntityType.PLAYER;
		PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false);

		PacketPlayOutTitle Title = new PacketPlayOutTitle(EnumTitleAction.TITLE,
				ChatSerializer.a("\"text\":\"You missed!\",\"color\":\"red\",\"bold\":true}"), 20, 40, 30);
		PacketPlayOutTitle SubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
				ChatSerializer.a("\"text\":\"Aim at the player!\",\"italic\":true,\"underlines\":true}"), 20, 40, 30);

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		if (eplayer == null) {
			player.addPotionEffect(blind);
			connection.sendPacket(Title);
			connection.sendPacket(SubTitle);
			player.playSound(player.getLocation(), Sound.BAT_HURT, 10, 10);
			player.sendMessage("§8[§6Badminton§8] §2You §cmissed§2.");
		}
	}

	@EventHandler
	public void onLeft(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack snowball = new ItemStack(Material.SNOW_BALL);

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand().getType().equals(Material.STICK) && player.getItemOnCursor().isSimilar(snowball)
					&& player.getItemInHand().hasItemMeta()) {
				
			}
		}
	}
}
