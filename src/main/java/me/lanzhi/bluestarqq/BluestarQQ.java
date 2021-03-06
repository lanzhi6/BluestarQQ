package me.lanzhi.bluestarqq;

import me.lanzhi.bluestarapi.api.config.YamlFile;
import me.lanzhi.bluestargameapi.BluestarGamePluginInterface;
import me.lanzhi.bluestarqq.commands.bindqq;
import me.lanzhi.bluestarqq.commands.maincommand;
import me.lanzhi.bluestarqq.type.reply;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class BluestarQQ extends JavaPlugin
{
    public static YamlFile config;
    public static Plugin plugin;
    public static YamlFile data;

    @Override
    public void onEnable()
    {
        ConfigurationSerialization.registerClass(reply.class);
        ConfigurationSerialization.registerClass(me.lanzhi.bluestarqq.type.bind.class);
        plugin=this;
        saveDefaultConfig();
        data=YamlFile.loadYamlFile(new File(plugin.getDataFolder(),"data.yml"));
        config=YamlFile.loadYamlFile(new File(plugin.getDataFolder(),"config.yml"));
        Bukkit.getPluginManager().registerEvents(new listener(),this);
        Objects.requireNonNull(getCommand("bluestarqq")).setExecutor(new maincommand());
        Objects.requireNonNull(getCommand("bindqq")).setExecutor(new bindqq());
        System.out.println("BluestarQQ已加载");
        System.out.println(Bukkit.getServicesManager().load(BluestarGamePluginInterface.class)==null);
    }

    @Override
    public void onDisable()
    {
        data.save();
        System.out.println("BluestarQQ已卸载");
    }
}
