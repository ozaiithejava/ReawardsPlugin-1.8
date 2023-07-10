/*    */ package me.ozaii.reawards.type;
/*    */ 
/*    */ public enum EnumRewardsType {
/*  4 */   GÜNLÜK("rewards.daily", 86400),
/*  5 */   HAFTALIK("rewards.weekly", 604800),
/*  6 */   VIP("rewards.vip", 604800),
/*  7 */   VIPPLUS("rewards.vipplus", 604800),
/*  8 */   MVIP("rewards.mvip", 604800),
/*  9 */   MVIPPLUS("rewards.mvipplus", 604800),
/* 10 */   RVIP("rewards.rvip", 604800),
/* 11 */   RVIPPLUS("rewards.rvipplus", 604800);
/*    */   
/*    */   private final String permission;
/*    */   private final int cooldown;
/*    */   
/*    */   EnumRewardsType(String permission, int cooldown) {
/* 17 */     this.permission = permission;
/* 18 */     this.cooldown = cooldown;
/*    */   }
/*    */   
/*    */   public String getPermission() {
/* 22 */     return this.permission;
/*    */   }
/*    */   
/*    */   public int getCooldown() {
/* 26 */     return this.cooldown;
/*    */   }
/*    */ }


/* Location:              C:\Users\ozaii1337\Desktop\masaüstü\Rutex\RutexLobby\plugins\Reawards.jar!\me\ozaii\reawards\type\EnumRewardsType.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */