/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.JJhack;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;

import static java.lang.Math.*;

public class JNewChunks extends Module{

   public JNewChunks(){
      super(Categories.JJHACK, "JJNewChunks", "Testing");
   }

   private final SettingGroup sgVars = settings.createGroup("Settings");

   private final Setting<Boolean> logOnChat = sgVars.add(new BoolSetting.Builder()
           .name("Log on chat")
           .description("Log on chat some debug stuff")
           .defaultValue(false)
           .build()
   );
   public enum JJNewChunksVersion {
      onExploit,
      all
   }


   public final Setting<JJNewChunksVersion> JJChunksSetting = sgVars.add(new EnumSetting.Builder<JJNewChunksVersion>()
           .name("Mode")
           .description("Render only onExploit or all")
           .defaultValue(JJNewChunksVersion.all)
           .build()
   );

   private final ArrayList<JChunks> loadedChunks = new ArrayList<>();

   @EventHandler
   private void onRender(Render3DEvent render3DEvent){
      for(JChunks JChunks : loadedChunks){
         if (mc.getCameraEntity().getBlockPos().isWithinDistance(JChunks.getStartPos(), 1024)){ // I have no idea
            render(render3DEvent, JChunks);
         }
      }
   }
   private void render(Render3DEvent event, JChunks chunk){
      event.renderer.boxLines(chunk.getX(), 0, chunk.getZ(), chunk.getX2(),0,chunk.getZ2(), Color.RED, 1);
   }

   private void addChunk(){
      JChunks chunk = new JChunks(mc.player.getChunkPos().getStartX(), mc.player.getChunkPos().getStartZ());

       //handle null
      if(loadedChunks.isEmpty()) {
         chunk.addChunk();
      }

      final JChunks lastChunk = loadedChunks.get(loadedChunks.size() - 1);
      if(abs(chunk.getX() - lastChunk.getX()) > 15.9
         || abs(chunk.getZ() - lastChunk.getZ()) > 15.9){
            chunk.addChunk();
         }
   }

   private boolean hasChanged = false;
   private long timeNew;
   private long timeOld;


   private String lastBar = "";
   @EventHandler
   private void onExploitOccur(TickEvent.Post event) {
      String actionBar = Modules.get().getActionBar();
      if(Math.round(mc.player.speed) < 19) return;
      if (actionBar == null || lastBar == null || actionBar.length() < 18) return;


      if (actionBar.charAt(18) == 'O') { // player is already on old chunks
         if (JJChunksSetting.get() == JJNewChunksVersion.all) {
            addChunk();
         }
      } else if(actionBar.charAt(18) == 'N'){
         if (actionBar.equals(lastBar)) {
            timeOld = System.currentTimeMillis();
            hasChanged = false;
         } else {
            timeNew = System.currentTimeMillis();
            hasChanged = true;
         }
         lastBar = actionBar;
      }
      if(hasChanged){
         if(logOnChat.get()) info("Exploit occur");
         if(timeNew - timeOld < 80){
            addChunk();
         }
      }
   }

   private class JChunks {
     private int x, z;

     public void addChunk(){
        loadedChunks.add(this);
     }
      public JChunks(int x, int z){
         setX(x);
         setZ(z);
      }
      public int getX() {
        return this.x;
      }
      public BlockPos getStartPos(){
         return new BlockPos(this.getX(), 0, this.getZ());
      }

      public void setX(int x) {
         this.x = x;
      }
      public int getZ() {
         return this.z;
      }
      public void setZ(int z) {
         this.z = z;
      }
      public double getX2() {
         return x + 16;
      }
      public double getZ2() {
         return z + 16;
      }

   }

}
