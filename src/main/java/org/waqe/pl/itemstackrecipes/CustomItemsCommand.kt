package org.waqe.pl.itemstackrecipes

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getLogger
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.lang.NullPointerException

class CustomItemsCommand(customItems: CustomItems) : CommandExecutor, Listener {

    val awesomeSword: ItemStack = ItemStack(Material.DIAMOND_SWORD)
    val pl = customItems

    init {

        pl.server.pluginManager.registerEvents(this, pl)

        //val awesomeSword = ItemStack(Material.DIAMOND_SWORD)
        val swordMeta = this.awesomeSword.itemMeta
        if (swordMeta != null) swordMeta.displayName = "Awesome Sword"
        val lore = ArrayList<String>()
        lore.add(" ")
        lore.add("This word does NOT break!")
        swordMeta?.lore = lore
        swordMeta?.isUnbreakable = true
        swordMeta?.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true)
        this.awesomeSword.itemMeta = swordMeta
    }

    @EventHandler
    fun onPlayerPrepareCraft(e: PrepareItemCraftEvent) {
        Bukkit.getLogger().info("PrepareItemCraftEvent......")
        var inv: CraftingInventory = e.getInventory()
        val matrix: Array<ItemStack> = inv.getMatrix()
        if (matrix.size < 9) {
            Bukkit.getLogger().info("log output......")
            return
        }
        try {
            if (matrix[1] != null && (matrix[1].type.equals(Material.DIAMOND_BLOCK))) {
                if (matrix[4] != null && (matrix[4].type.equals(Material.DIAMOND_BLOCK))) {
                    if (matrix[7] != null && (matrix[7].type.equals(Material.STICK))) {
                                Bukkit.getLogger().info("adding awesomesword")
                                inv.setResult(this.awesomeSword)
                    }
                }
            }
        } catch(e: NullPointerException) {
            Bukkit.getLogger().info("pointer pain......")
            e.printStackTrace()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Command only for player use.")
            return true
        }

        val player = sender.player

        if (!player.hasPermission("customitems.main")) {
            player.sendMessage("You need the permission: customitems.main")
            return true
        }
        if (args.size < 1) {
            player.sendMessage("You need to specify arguments.")
            return true
        }
        try {
            player.sendMessage("arguments sent = ${args.size}")
            if (args.get(0).equals("awesomesword", ignoreCase = true)) {
                if (!player.hasPermission("customitems.summon.sword")) {
                    player.sendMessage("You need the permission: customitems.summon.sword")
                }
                player.inventory.addItem(awesomeSword)
                player.sendMessage("You have been given the item " + args[0])
            } else {
                player.sendMessage("Not valid item.")
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }

        return true
    }
}