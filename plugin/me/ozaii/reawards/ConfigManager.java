/*    */ package me.ozaii.reawards;
/*    */ 
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigManager
/*    */ {
/*    */   private static FileConfiguration config;
/*    */   
/*    */   public static void setupConfig(Reawards reawards) {
/* 15 */     config = reawards.getConfig();
/* 16 */     reawards.saveDefaultConfig();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String Mysql_name() {
/* 23 */     return config.getString("Mysql_name");
/* 24 */   } public static String Mysql_pass() { return config.getString("Mysql_pass"); } public static String Mysql_host() {
/* 25 */     return config.getString("Mysql_host");
/*    */   }
/*    */ }


/* Location:              C:\Users\ozaii1337\Desktop\masaüstü\Rutex\RutexLobby\plugins\Reawards.jar!\me\ozaii\reawards\ConfigManager.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */