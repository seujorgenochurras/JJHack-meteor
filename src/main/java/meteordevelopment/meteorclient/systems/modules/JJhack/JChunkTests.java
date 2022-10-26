/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.JJhack;

import baritone.api.event.events.ChunkEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;

public class JChunkTests extends Module{

   public JChunkTests(){
      super(Categories.JJHACK, "JChunkExploit", "Testing");
   }


   @EventHandler
   private void onChunkLoad(ChunkEvent chunkEvent, Render3DEvent render3DEvent){
         render3DEvent.renderer.box(chunkEvent.getX(), 2, chunkEvent.getZ(), chunkEvent.getX() + 10, 4, chunkEvent.getZ() + 10 ,
              Color.BLUE ,Color.RED, ShapeMode.Both, 1);
      System.out.println(chunkEvent.getX());
   }

   private void render(Render3DEvent event, ChunkEvent chunkEvent){
      event.renderer.box(chunkEvent.getX(), 2, chunkEvent.getZ(), chunkEvent.getX() + 10, 4, chunkEvent.getZ() + 10 ,
              Color.BLUE ,Color.RED, ShapeMode.Both, 1);
   }

}
