package org.enchantedskies.activityrewarder.rewardtypes;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdReward implements Reward {
    private String command;
    private final String size;
    private final double hourlyAmount;

    public CmdReward(String commandReward, String size, int count, int hours) {
        this.command = commandReward;
        this.size = size;
        this.hourlyAmount = 0.5 * 17;
    }

    public CmdReward(String commandReward, String size) {
        this.command = commandReward;
        this.size = size;
        this.hourlyAmount = 0;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public void giveReward(Player player) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        command = command.replace("%user%", player.getName()).replace("%hourly-amount%", String.valueOf((int) Math.floor(hourlyAmount)));
        Bukkit.dispatchCommand(console, command);
    }
}
