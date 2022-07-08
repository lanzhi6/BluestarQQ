package me.lanzhi.bluestarqq.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.lanzhi.bluestargameapi.BluestarGamePluginInterface;
import me.lanzhi.bluestarqq.type.bind;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class bindqq implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label,@NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED+"仅允许玩家输入!");
            return true;
        }
        Player player=(Player) sender;
        if (args.length!=1)
        {
            sender.sendMessage(ChatColor.RED+"错误!请输入qq号");
            return false;
        }
        bind binds=bind.get();
        long id;
        try
        {
            id=Long.parseLong(args[0]);
        }
        catch (NumberFormatException e)
        {
            sender.sendMessage(ChatColor.RED+"错误!");
            return false;
        }
        if (MiraiMC.getBind(id)!=null)
        {
            BluestarGamePluginInterface bluestarGamePlugin=Bukkit.getServicesManager().load(BluestarGamePluginInterface.class);
            if (bluestarGamePlugin==null)
            {
                System.out.println(ChatColor.RED+"错误,BluestarGame为null");
                sender.sendMessage(ChatColor.RED+"出现错误,错误代码: bsgnull");
                return true;
            }
            if (bluestarGamePlugin.getBluestarGameManager().getApi().getXiaoMoBank().getBorrow(MiraiMC.getBind(id))!=0)
            {
                sender.sendMessage(ChatColor.RED+"此qq号已绑定Minecraft账号,且绑定的账号有银行贷款未偿还,无法解绑");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED+"检测到您的qq号已绑定账号,若您完成绑定,将与之前的Minecraft账号解绑");
            }
        }
        if (MiraiMC.getBinding(((Player) sender).getUniqueId().toString())!=0)
        {
            sender.sendMessage(ChatColor.RED+"检测到您的Minecraft已绑定qq,若您完成绑定,将与之前的qq账号解绑");
        }
        long key=Math.abs(ThreadLocalRandom.current().nextLong()%900000)+100000;
        binds.addbind(((Player) sender).getUniqueId(),id,key);
        sender.sendMessage(ChatColor.GOLD+"请使用qq号:"+ChatColor.RED+id+ChatColor.GOLD+"向群聊(聊天群或主群均可)或加好友后私信发送验证码: "+ChatColor.RED+key+ChatColor.GOLD+" 即可完成验证");
        binds.save();
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,@NotNull Command command,@NotNull String alias,@NotNull String[] args)
    {
        if (args.length==1)
        {
            List<String> tablist=new ArrayList<>();
            tablist.add("您的qq号");
            return tablist;
        }
        return new ArrayList<>();
    }
}
