/*     */ package me.ozaii.reawards.commands;
/*     */ 
/*     */ import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
/*     */ import com.yapzhenyie.GadgetsMenu.player.PlayerManager;
/*     */ import com.yapzhenyie.GadgetsMenu.utils.mysteryboxes.MysteryBoxType;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import me.ozaii.coin.CoinAPI;
/*     */ import me.ozaii.reawards.ConfigManager;
/*     */ import me.ozaii.reawards.type.EnumRewardsType;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ public class RewardsCommand
/*     */   implements Listener, CommandExecutor
/*     */ {
/*     */   private JavaPlugin plugin;
/*     */   private static Connection connection;
/*     */   
/*     */   public RewardsCommand(JavaPlugin plugin) {
/*  41 */     this.plugin = plugin;
/*  42 */     plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
/*     */ 
/*     */     
/*  45 */     String host = "localhost";
/*  46 */     int port = 3306;
/*  47 */     String database = ConfigManager.Mysql_host();
/*  48 */     String username = ConfigManager.Mysql_name();
/*  49 */     String password = ConfigManager.Mysql_pass();
/*     */     
/*     */     try {
/*  52 */       connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, 
/*  53 */           password);
/*  54 */       createTable();
/*  55 */     } catch (SQLException e) {
/*  56 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/*  64 */     Player player = (Player)event.getWhoClicked();
/*  65 */     Inventory inventory = event.getInventory();
/*  66 */     ItemStack clickedItem = event.getCurrentItem();
/*     */     
/*  68 */     if (inventory.getTitle().equals(ChatColor.GOLD + "Ödüller")) {
/*  69 */       event.setCancelled(true);
/*     */       
/*  71 */       if (clickedItem != null && clickedItem.getType() == Material.ENDER_CHEST) {
/*  72 */         String rewardTypeName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
/*  73 */         EnumRewardsType rewardType = EnumRewardsType.valueOf(rewardTypeName.toUpperCase());
/*     */         
/*  75 */         if (rewardType != null) {
/*  76 */           if (!hasPermission(player, rewardType.getPermission())) {
/*  77 */             player.sendMessage(ChatColor.RED + "Bu ödülü almak için gerekli izne sahip değilsiniz!");
/*  78 */             player.closeInventory();
/*     */             
/*     */             return;
/*     */           } 
/*  82 */           if (!canClaimReward(player, rewardType)) {
/*  83 */             player.sendMessage(ChatColor.RED + "Bu ödülü zaten aldınız!");
/*  84 */             long l = getRewardCooldown(player, rewardType);
/*  85 */             if (l > 0L) {
/*  86 */               String remainingTime = formatTime(l);
/*  87 */               player.sendMessage(ChatColor.RED + "Tekrar alabilmek için kalan süre: " + ChatColor.GOLD + 
/*  88 */                   remainingTime);
/*     */             } 
/*  90 */             player.closeInventory();
/*     */             
/*     */             return;
/*     */           } 
/*  94 */           long cooldown = getRewardCooldown(player, rewardType);
/*  95 */           if (cooldown > 0L) {
/*  96 */             player.sendMessage(ChatColor.RED + "Bu ödülü tekrar alabilmek için " + ChatColor.GOLD + 
/*  97 */                 formatTime(cooldown) + " beklemeniz gerekiyor!");
/*  98 */             player.closeInventory();
/*     */             
/*     */             return;
/*     */           } 
/* 102 */           double rewardAmount = getRewardAmount(rewardType);
/* 103 */           CoinAPI coinAPI = (CoinAPI)Bukkit.getPluginManager().getPlugin("CoinAPI");
/*     */           
/* 105 */           coinAPI.addPlayerCoins(player.getName(), getRewardAmount(rewardType));
/* 106 */           PlayerManager playerManager = GadgetsMenuAPI.getPlayerManager(player);
/*     */ 
/*     */ 
/*     */           
/* 110 */           if (player.hasPermission("rewards.vip")) {
/* 111 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_1, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 2);
/* 112 */           } else if (player.hasPermission("rewards.vipplus")) {
/* 113 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_2, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 2);
/* 114 */           } else if (player.hasPermission("rewards.mvip")) {
/* 115 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_3, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 2);
/* 116 */           } else if (player.hasPermission("rewards.mvipplus")) {
/* 117 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_4, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 2);
/* 118 */           } else if (player.hasPermission("rewards.rvip")) {
/* 119 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_1, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 120 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_2, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 121 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_3, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 122 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_4, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 123 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_5, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 2);
/* 124 */           } else if (player.hasPermission("rewards.rvipplus")) {
/* 125 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_1, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 126 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_2, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 127 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_3, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 128 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_4, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/* 129 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_5, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 3);
/* 130 */           } else if (player.hasPermission("rewards.rewards.weekly")) {
/* 131 */             playerManager.giveMysteryBoxes(MysteryBoxType.NORMAL_MYSTERY_BOX_1, Long.valueOf(System.currentTimeMillis() + 1492828928L), false, null, 1);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 136 */           setRewardClaimed(player, rewardType);
/*     */           
/* 138 */           player.sendMessage(ChatColor.GREEN + "Ödülü aldınız!");
/* 139 */           player.closeInventory();
/*     */ 
/*     */           
/* 142 */           ItemStack chest = new ItemStack(Material.CHEST);
/* 143 */           ItemMeta chestMeta = chest.getItemMeta();
/* 144 */           chestMeta.setDisplayName(ChatColor.YELLOW + rewardType.name());
/* 145 */           chest.setItemMeta(chestMeta);
/*     */           
/* 147 */           int clickedSlot = event.getSlot();
/* 148 */           inventory.setItem(clickedSlot, chest);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasPermission(Player player, String permission) {
/* 155 */     return player.hasPermission(permission);
/*     */   }
/*     */   
/*     */   private boolean canClaimReward(Player player, EnumRewardsType rewardType) {
/*     */     try {
/* 160 */       PreparedStatement statement = connection
/* 161 */         .prepareStatement("SELECT claimed FROM rewards WHERE player = ? AND reward_type = ?");
/* 162 */       statement.setString(1, player.getUniqueId().toString());
/* 163 */       statement.setString(2, rewardType.name());
/* 164 */       ResultSet resultSet = statement.executeQuery();
/*     */       
/* 166 */       if (resultSet.next()) {
/* 167 */         boolean claimed = resultSet.getBoolean("claimed");
/* 168 */         return !claimed;
/*     */       } 
/* 170 */     } catch (SQLException e) {
/* 171 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   private long getRewardCooldown(Player player, EnumRewardsType rewardType) {
/*     */     try {
/* 179 */       PreparedStatement statement = connection
/* 180 */         .prepareStatement("SELECT claimed_date FROM rewards WHERE player = ? AND reward_type = ?");
/* 181 */       statement.setString(1, player.getUniqueId().toString());
/* 182 */       statement.setString(2, rewardType.name());
/* 183 */       ResultSet resultSet = statement.executeQuery();
/*     */       
/* 185 */       if (resultSet.next()) {
/* 186 */         long claimedDate = resultSet.getLong("claimed_date");
/* 187 */         long currentTime = System.currentTimeMillis();
/* 188 */         long cooldown = (rewardType.getCooldown() * 1000) - currentTime - claimedDate;
/* 189 */         return Math.max(cooldown, 0L);
/*     */       } 
/* 191 */     } catch (SQLException e) {
/* 192 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 195 */     return 0L;
/*     */   }
/*     */   
/*     */   private double getRewardAmount(EnumRewardsType rewardType) {
/* 199 */     switch (rewardType) {
/*     */       case null:
/* 201 */         return 10.0D;
/*     */       case HAFTALIK:
/* 203 */         return 20.0D;
/*     */       case VIP:
/* 205 */         return 30.0D;
/*     */       case VIPPLUS:
/* 207 */         return 40.0D;
/*     */       case MVIP:
/* 209 */         return 50.0D;
/*     */       case MVIPPLUS:
/* 211 */         return 60.0D;
/*     */       case RVIP:
/* 213 */         return 70.0D;
/*     */       case RVIPPLUS:
/* 215 */         return 80.0D;
/*     */     } 
/* 217 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setRewardClaimed(Player player, EnumRewardsType rewardType) {
/*     */     try {
/* 223 */       PreparedStatement statement = connection.prepareStatement(
/* 224 */           "INSERT INTO rewards (player, reward_type, claimed, claimed_date) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE claimed = ?, claimed_date = ?");
/* 225 */       statement.setString(1, player.getUniqueId().toString());
/* 226 */       statement.setString(2, rewardType.name());
/* 227 */       statement.setBoolean(3, true);
/* 228 */       statement.setLong(4, System.currentTimeMillis());
/* 229 */       statement.setBoolean(5, true);
/* 230 */       statement.setLong(6, System.currentTimeMillis());
/* 231 */       statement.executeUpdate();
/* 232 */     } catch (SQLException e) {
/* 233 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void giveReward(Player player, Material material, int amount) {
/* 238 */     ItemStack itemStack = new ItemStack(material, amount);
/* 239 */     player.getInventory().addItem(new ItemStack[] { itemStack });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String formatTime(long milliseconds) {
/* 246 */     long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
/* 247 */     long minutes = seconds / 60L;
/* 248 */     long hours = minutes / 60L;
/* 249 */     long days = hours / 24L;
/*     */     
/* 251 */     seconds %= 60L;
/* 252 */     minutes %= 60L;
/* 253 */     hours %= 24L;
/*     */     
/* 255 */     StringBuilder sb = new StringBuilder();
/* 256 */     if (days > 0L) {
/* 257 */       sb.append(days).append(" gün ");
/*     */     }
/* 259 */     if (hours > 0L) {
/* 260 */       sb.append(hours).append(" saat ");
/*     */     }
/* 262 */     if (minutes > 0L) {
/* 263 */       sb.append(minutes).append(" dakika ");
/*     */     }
/* 265 */     if (seconds > 0L) {
/* 266 */       sb.append(seconds).append(" saniye");
/*     */     }
/*     */     
/* 269 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private void sendActionBar(Player player, String message) {
/* 273 */     player.spigot().sendMessage(TextComponent.fromLegacyText(message));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 278 */     if (!(sender instanceof Player)) {
/* 279 */       sender.sendMessage(ChatColor.RED + "Bu komutu yalnızca oyuncular kullanabilir!");
/* 280 */       return true;
/*     */     } 
/*     */     
/* 283 */     Player player = (Player)sender;
/* 284 */     openRewardsMenu(player);
/* 285 */     return true;
/*     */   }
/*     */   
/*     */   private void openRewardsMenu(Player player) {
/* 289 */     Inventory inventory = this.plugin.getServer().createInventory(null, 9, ChatColor.GOLD + "Ödüller"); byte b; int i;
/*     */     EnumRewardsType[] arrayOfEnumRewardsType;
/* 291 */     for (i = (arrayOfEnumRewardsType = EnumRewardsType.values()).length, b = 0; b < i; ) { EnumRewardsType rewardType = arrayOfEnumRewardsType[b];
/* 292 */       ItemStack itemStack = new ItemStack(Material.ENDER_CHEST);
/* 293 */       ItemMeta itemMeta = itemStack.getItemMeta();
/*     */       
/* 295 */       if (rewardType.name() == "RVIPPLUS") {
/* 296 */         itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "RVIPPLUS");
/* 297 */       } else if (rewardType.name() == "RVIP") {
/* 298 */         itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "RVIP");
/* 299 */       } else if (rewardType.name() == "MVIPPLUS") {
/* 300 */         itemMeta.setDisplayName(ChatColor.GREEN + "MVIPPLUS");
/* 301 */       } else if (rewardType.name() == "MVIP") {
/* 302 */         itemMeta.setDisplayName(ChatColor.GREEN + "MVIP");
/* 303 */       } else if (rewardType.name() == "VIPPLUS") {
/* 304 */         itemMeta.setDisplayName(ChatColor.GOLD + "VIPPLUS");
/* 305 */       } else if (rewardType.name() == "VIP") {
/* 306 */         itemMeta.setDisplayName(ChatColor.GOLD + "VIP");
/*     */       } else {
/* 308 */         itemMeta.setDisplayName(ChatColor.YELLOW + rewardType.name());
/*     */       } 
/*     */ 
/*     */       
/* 312 */       itemStack.setItemMeta(itemMeta);
/*     */       
/* 314 */       inventory.addItem(new ItemStack[] { itemStack });
/*     */       b++; }
/*     */     
/* 317 */     player.openInventory(inventory);
/*     */   }
/*     */   
/*     */   public static void createTable() {
/*     */     try {
/* 322 */       Statement statement = connection.createStatement();
/* 323 */       String query = "CREATE TABLE IF NOT EXISTS rewards (id INT AUTO_INCREMENT PRIMARY KEY,player VARCHAR(255) NOT NULL,reward_type VARCHAR(255) NOT NULL,claimed TINYINT(1) NOT NULL,claimed_date BIGINT(20) NOT NULL)";
/*     */ 
/*     */       
/* 326 */       statement.executeUpdate(query);
/* 327 */     } catch (SQLException e) {
/* 328 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ozaii1337\Desktop\masaüstü\Rutex\RutexLobby\plugins\Reawards.jar!\me\ozaii\reawards\commands\RewardsCommand.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */