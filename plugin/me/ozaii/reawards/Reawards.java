/*    */ package me.ozaii.reawards;
/*    */ 
/*    */ import me.ozaii.reawards.commands.RewardsCommand;
/*    */ import me.ozaii.reawards.messages.Messages;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Reawards
/*    */   extends JavaPlugin {
/*    */   private ConfigManager configManager;
/*    */   
/*    */   public void onEnable() {
/* 13 */     ConfigManager.setupConfig(this);
/* 14 */     getCommand("oduller").setExecutor((CommandExecutor)new RewardsCommand(this));
/* 15 */     getLogger().info(Messages.enableConsoleMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 20 */     getLogger().info(Messages.disableConsoleMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\ozaii1337\Desktop\masaüstü\Rutex\RutexLobby\plugins\Reawards.jar!\me\ozaii\reawards\Reawards.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */