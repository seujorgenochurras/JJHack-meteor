/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.JJhack;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;


import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.*;

public class JChunkTests extends Module{

   public JChunkTests(){
      super(Categories.JJHACK, "JChunkExploit", "Testing");
   }

   private final SettingGroup sgVars = settings.createGroup("variaveis");

   private final Setting<Integer> x = sgVars.add(new IntSetting.Builder()
           .name("X")
           .description("aaa")
                   .defaultValue(128)
                   .sliderMax(300)
           .build()
   );
   private final Setting<Integer> z = sgVars.add(new IntSetting.Builder()
           .name("z")
           .description("aaa")
           .defaultValue(128)

           .sliderMax(300)
           .build()
   );
   private final Setting<Boolean> logOnChat = sgVars.add(new BoolSetting.Builder()
           .name("log")
           .description("aaa")
           .defaultValue(false)
           .build()
   );
   private final Setting<Integer> x2 = sgVars.add(new IntSetting.Builder()
           .name("X2")
           .description("aaa")
           .defaultValue(128)

           .sliderMax(300)
           .build()
   );
   private final Setting<Integer> z2 = sgVars.add(new IntSetting.Builder()
           .name("z2")
           .description("aaa")
           .defaultValue(128)
           .sliderMax(300)
           .build()
   );

   private final Setting<Integer> excludeSmth = sgVars.add(new IntSetting.Builder()
           .name("excludeDir")
           .description("aaa")
           .defaultValue(1)
           .sliderMax(8)
           .build()
   );

   private final ArrayList<JChunks> loadedChunks = new ArrayList<>();

   @EventHandler
   private void onRender(Render3DEvent render3DEvent){
      for(JChunks JChunks : loadedChunks){
         render(render3DEvent, JChunks);
      }
   }
   private void render(Render3DEvent event, JChunks chunk){
      event.renderer.boxLines(chunk.getX(), 0, chunk.getZ(), chunk.getX2(),0,chunk.getZ2(), Color.RED, excludeSmth.get());
   }
   private JChunks chunk;
   @EventHandler
   private void onWalk(TickEvent.Post event){

       chunk = new JChunks(mc.player.getChunkPos().getStartX(), mc.player.getChunkPos().getStartZ());

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
   private boolean hasChangedCopy = false;
   private long timeNew;
   private long timeOld;


   private String lastBar = "";

   @EventHandler
   private void onExploitOccur(TickEvent.Pre event) {
      String actionBar = Modules.get().getActionBar();
      if (actionBar == null || lastBar == null || actionBar.length() < 18) return;

      if (actionBar.charAt(18) == 'N' || actionBar.charAt(18) == 'O') {
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
         if(timeNew - timeOld < 80){
            if(logOnChat.get()) System.out.println(timeNew - timeOld);
            mc.player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 21, 21);
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
      public void setX(int x) {
         this.x = x + 128;
      }
      public int getZ() {
         return this.z;
      }
      public void setZ(int z) {
         this.z = z + 128;
      }
      public double getX2() {
         return x + 144;
      }
      public double getZ2() {
         return z + 144;
      }

   }

}
