/*    */ package me.ozaii.reawards.messages;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ 
/*    */ public class Messages
/*    */ {
/*    */   public static String enableConsoleMessage() {
/*  9 */     String logo = "                            \n                        |__/                | $$                    | $$      \n /$$  /$$  /$$  /$$$$$$  /$$ /$$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$ | $$   /$$\n| $$ | $$ | $$ /$$__  $$| $$| $$__  $$ /$$__  $$ |____  $$ /$$__  $$| $$  /$$/\n| $$ | $$ | $$| $$$$$$$$| $$| $$  \\ $$| $$  | $$  /$$$$$$$| $$  \\__/| $$$$$$/ \n| $$ | $$ | $$| $$_____/| $$| $$  | $$| $$  | $$ /$$__  $$| $$      | $$_  $$ \n|  $$$$$/$$$$/|  $$$$$$$| $$| $$  | $$|  $$$$$$$|  $$$$$$$| $$      | $$ \\  $$\n \\_____/\\___/  \\_______/|__/|__/  |__/ \\_______/ \\_______/|__/      |__/  \\__/\n                                                                              \n  Friend Plugini Aktif                                                      \nmade by: ozaii date: 24/06/2023 for: weindark lisance type: MIT";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 21 */     String greenLogo = "\033[32m" + logo + "\033[0m";
/* 22 */     return greenLogo;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String disableConsoleMessage() {
/* 27 */     String logo = "                            \n                        |__/                | $$                    | $$      \n /$$  /$$  /$$  /$$$$$$  /$$ /$$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$ | $$   /$$\n| $$ | $$ | $$ /$$__  $$| $$| $$__  $$ /$$__  $$ |____  $$ /$$__  $$| $$  /$$/\n| $$ | $$ | $$| $$$$$$$$| $$| $$  \\ $$| $$  | $$  /$$$$$$$| $$  \\__/| $$$$$$/ \n| $$ | $$ | $$| $$_____/| $$| $$  | $$| $$  | $$ /$$__  $$| $$      | $$_  $$ \n|  $$$$$/$$$$/|  $$$$$$$| $$| $$  | $$|  $$$$$$$|  $$$$$$$| $$      | $$ \\  $$\n \\_____/\\___/  \\_______/|__/|__/  |__/ \\_______/ \\_______/|__/      |__/  \\__/\n                                                                              \n  Friend Plugini Deaktif!                                                      \nmade by: ozaii date: 24/06/2023 for: weindark lisance type: MIT";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     String greenLogo = "\033[32m" + logo + "\033[0m";
/* 40 */     return greenLogo;
/*    */   }
/*    */   
/*    */   public static String odulVerildiMessage() {
/* 44 */     String c = ChatColor.GREEN + "Tebrikler Ödülünü Başarı İle Aldın";
/* 45 */     return c;
/*    */   }
/*    */   
/*    */   public static String odulYetkiYokMessage() {
/* 49 */     String c = ChatColor.RED + "Üzgünüm Bu Ödüle Hak Kazanamadın!";
/* 50 */     return c;
/*    */   }
/*    */   
/*    */   public static String odulAlındıZatenMesage() {
/* 54 */     String c = ChatColor.RED + "Üzgünüm Bu Ödüle Hak Kazanamadın!";
/* 55 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\ozaii1337\Desktop\masaüstü\Rutex\RutexLobby\plugins\Reawards.jar!\me\ozaii\reawards\messages\Messages.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */