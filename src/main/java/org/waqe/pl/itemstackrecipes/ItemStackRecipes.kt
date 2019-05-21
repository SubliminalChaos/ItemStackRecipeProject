package org.waqe.pl.itemstackrecipes

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class CustomItems : JavaPlugin() {

    override fun onEnable() {
        try {
            getCommand("customitems").executor = CustomItemsCommand(this)
        } catch(e: Exception) {
            e.printStackTrace()
        }
        Bukkit.getLogger().info("onEnable......")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}