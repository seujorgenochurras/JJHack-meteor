/*
 * This file is NOT part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 * I DONT KNOW HOW TO CODE IM DOING JUST A LITTLE BIT OF TROLLING HERE AND THERE...
 */

package meteordevelopment.meteorclient.systems.modules.JJhack;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.loader.impl.util.log.Log;

public class JAutoSprint extends Module {


    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public enum sprintWhen {
        Always,
        Crouch
    }

    private final Setting<sprintWhen> sprintWhenSetting = sgGeneral.add(new EnumSetting.Builder<sprintWhen>()
        .name("Sprint when:")
        .description("Choose the conditions to sprint")
        .defaultValue(sprintWhen.Always)
        .build()
    );
    private boolean sprintWhen(){
            if(mc.player.isSneaking()){
                mc.player.setSprinting(false);
            }
            return switch (sprintWhenSetting.get()){
                case Always -> true;
                case Crouch -> !mc.player.isSneaking();
            };
    }
    public JAutoSprint(){
        super(Categories.JJHACK, "JAutoSprint", "Automatically sprints for you"); //Criando o hack
    }

    @EventHandler
    private void onTick(TickEvent.Post e){
        if(sprintWhen()) {
            mc.player.setSprinting(true);
        }
        if(mc.player.isFallFlying()){
            info("isFallFlying(true)");
        }
    }

    @Override
    public void onDeactivate(){
        mc.player.setSprinting(false);
    }


}
