/*
 * This file is part of UltimateCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) Bammerbom
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bammerbom.ultimatecore.bukkit.listeners;

import bammerbom.ultimatecore.bukkit.api.UC;
import bammerbom.ultimatecore.bukkit.r;
import bammerbom.ultimatecore.bukkit.resources.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Blaze;
import static org.bukkit.entity.EntityType.BAT;
import static org.bukkit.entity.EntityType.BLAZE;
import static org.bukkit.entity.EntityType.CAVE_SPIDER;
import static org.bukkit.entity.EntityType.CHICKEN;
import static org.bukkit.entity.EntityType.COMPLEX_PART;
import static org.bukkit.entity.EntityType.COW;
import static org.bukkit.entity.EntityType.CREEPER;
import static org.bukkit.entity.EntityType.DROPPED_ITEM;
import static org.bukkit.entity.EntityType.ENDERMAN;
import static org.bukkit.entity.EntityType.ENDERMITE;
import static org.bukkit.entity.EntityType.ENDER_CRYSTAL;
import static org.bukkit.entity.EntityType.ENDER_DRAGON;
import static org.bukkit.entity.EntityType.ENDER_SIGNAL;
import static org.bukkit.entity.EntityType.EXPERIENCE_ORB;
import static org.bukkit.entity.EntityType.FALLING_BLOCK;
import static org.bukkit.entity.EntityType.FISHING_HOOK;
import static org.bukkit.entity.EntityType.GHAST;
import static org.bukkit.entity.EntityType.GIANT;
import static org.bukkit.entity.EntityType.GUARDIAN;
import static org.bukkit.entity.EntityType.HORSE;
import static org.bukkit.entity.EntityType.IRON_GOLEM;
import static org.bukkit.entity.EntityType.LEASH_HITCH;
import static org.bukkit.entity.EntityType.LIGHTNING;
import static org.bukkit.entity.EntityType.MAGMA_CUBE;
import static org.bukkit.entity.EntityType.MINECART_CHEST;
import static org.bukkit.entity.EntityType.MINECART_COMMAND;
import static org.bukkit.entity.EntityType.MINECART_FURNACE;
import static org.bukkit.entity.EntityType.MINECART_HOPPER;
import static org.bukkit.entity.EntityType.MINECART_MOB_SPAWNER;
import static org.bukkit.entity.EntityType.MINECART_TNT;
import static org.bukkit.entity.EntityType.MUSHROOM_COW;
import static org.bukkit.entity.EntityType.OCELOT;
import static org.bukkit.entity.EntityType.PIG;
import static org.bukkit.entity.EntityType.PIG_ZOMBIE;
import static org.bukkit.entity.EntityType.PLAYER;
import static org.bukkit.entity.EntityType.PRIMED_TNT;
import static org.bukkit.entity.EntityType.SHEEP;
import static org.bukkit.entity.EntityType.SILVERFISH;
import static org.bukkit.entity.EntityType.SKELETON;
import static org.bukkit.entity.EntityType.SLIME;
import static org.bukkit.entity.EntityType.SMALL_FIREBALL;
import static org.bukkit.entity.EntityType.SNOWBALL;
import static org.bukkit.entity.EntityType.SNOWMAN;
import static org.bukkit.entity.EntityType.SPIDER;
import static org.bukkit.entity.EntityType.SPLASH_POTION;
import static org.bukkit.entity.EntityType.SQUID;
import static org.bukkit.entity.EntityType.THROWN_EXP_BOTTLE;
import static org.bukkit.entity.EntityType.UNKNOWN;
import static org.bukkit.entity.EntityType.VILLAGER;
import static org.bukkit.entity.EntityType.WEATHER;
import static org.bukkit.entity.EntityType.WITCH;
import static org.bukkit.entity.EntityType.WITHER;
import static org.bukkit.entity.EntityType.WITHER_SKULL;
import static org.bukkit.entity.EntityType.WOLF;
import static org.bukkit.entity.EntityType.ZOMBIE;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.CONTACT;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.CUSTOM;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.DROWNING;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.MELTING;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.PROJECTILE;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.STARVATION;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUICIDE;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DeathmessagesListener implements Listener {

    public static void start() {
        Bukkit.getPluginManager().registerEvents(new DeathmessagesListener(), r.getUC());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void death(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        EntityDamageEvent damageEvent = p.getLastDamageCause();
        if (e.getDeathMessage() == null || e.getDeathMessage().equalsIgnoreCase("")) {
            return;
        }
        if (damageEvent.getCause() != null) {
            switch (damageEvent.getCause()) {
                case BLOCK_EXPLOSION:
                    e.setDeathMessage(r.mes("deathmessageTNT").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case CONTACT:
                    e.setDeathMessage(r.mes("deathmessageCactus").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case CUSTOM:
                    e.setDeathMessage(r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case DROWNING:
                    e.setDeathMessage(r.mes("deathmessageDrowning").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case ENTITY_ATTACK:
                    break;
                case ENTITY_EXPLOSION:
                    e.setDeathMessage(r.mes("deathmessageCreeper").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case FALL:
                    e.setDeathMessage(r.mes("deathmessageFall").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case FALLING_BLOCK:
                    e.setDeathMessage(r.mes("deathmessageFallingBlock").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case FIRE:
                    e.setDeathMessage(r.mes("deathmessageFire").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case FIRE_TICK:
                    e.setDeathMessage(r.mes("deathmessageFire").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case LAVA:
                    e.setDeathMessage(r.mes("deathmessageLava").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case LIGHTNING:
                    e.setDeathMessage(r.mes("deathmessageLightning").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case MAGIC:
                    e.setDeathMessage(r.mes("deathmessagePotion").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case MELTING:
                    e.setDeathMessage(r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case POISON:
                    e.setDeathMessage(r.mes("deathmessagePotion").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case PROJECTILE:
                    e.setDeathMessage(r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case STARVATION:
                    e.setDeathMessage(r.mes("deathmessageHunger").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case SUFFOCATION:
                    e.setDeathMessage(r.mes("deathmessageSuffocated").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case SUICIDE:
                    e.setDeathMessage(r.mes("deathmessageSuicide").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case THORNS:
                    e.setDeathMessage(r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case VOID:
                    e.setDeathMessage(r.mes("deathmessageOutOfWorld").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                case WITHER:
                    e.setDeathMessage(r.mes("deathmessageWither").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    break;
                default:
                    break;
            }
            String ep = en(damageEvent);
            if (!ep.equalsIgnoreCase("FALSE")) {
                e.setDeathMessage(ep);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public String en(EntityDamageEvent damageEvent) {
        Player p = (Player) damageEvent.getEntity();
        if (damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) damageEvent;
            switch (ev.getDamager().getType()) {
                case ARROW:
                    Projectile pr = (Projectile) ev.getDamager();
                    if (((ProjectileSource) pr.getShooter()) instanceof Player) {
                        Player k = (Player) pr.getShooter();
                        String name = "a Bow";
                        if (k.getItemInHand().hasItemMeta() && k.getItemInHand().getItemMeta().hasDisplayName()) {
                            name = k.getItemInHand().getItemMeta().getDisplayName();
                        }
                        return r.mes("deathmessagePlayer").replaceAll("%Killed", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())).replaceAll("%Killer", (UC.getPlayer((Player) pr.getShooter()).getNick())).replaceAll("%Weapon", name);

                    } else if (pr.getShooter() instanceof Skeleton) {
                        Skeleton sk = (Skeleton) pr.getShooter();
                        if (sk.getSkeletonType().equals(SkeletonType.NORMAL)) {
                            return (r.mes("deathmessageSkeleton").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                        } else {
                            return (r.mes("deathmessageWitherSkeleton").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                        }

                    } else if (pr.getShooter() instanceof Ghast) {
                        return (r.mes("deathmessageGhast").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    } else if (pr.getShooter() instanceof Blaze) {
                        return (r.mes("deathmessageBlaze").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    } else {
                        return (r.mes("deathmessageProjectile").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    }

                case BAT:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case BLAZE:
                    return (r.mes("deathmessageBlaze").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case BOAT:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case CAVE_SPIDER:
                    return (r.mes("deathmessageCaveSpider").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case CHICKEN:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case COMPLEX_PART:
                    return (r.mes("deathmessageEnderDragon").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case COW:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case CREEPER:
                    return (r.mes("deathmessageCreeper").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case DROPPED_ITEM:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case EGG:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ENDERMAN:
                    return (r.mes("deathmessageEnderman").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ENDER_CRYSTAL:
                    return (r.mes("deathmessageEnderCrystal").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ENDER_DRAGON:
                    return (r.mes("deathmessageEnderDragon").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ENDER_PEARL:
                    return (r.mes("deathmessageEnderpearl").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ENDER_SIGNAL:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case EXPERIENCE_ORB:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case FALLING_BLOCK:
                    return (r.mes("deathmessageSuffocated").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case FIREBALL:
                    return (r.mes("deathmessageGhast").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case FIREWORK:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case FISHING_HOOK:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case GHAST:
                    return (r.mes("deathmessageGhast").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case GIANT:
                    return (r.mes("deathmessageGiant").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case HORSE:
                    return (r.mes("deathmessageHorse").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case IRON_GOLEM:
                    return (r.mes("deathmessageIronGolem").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ITEM_FRAME:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case LEASH_HITCH:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case LIGHTNING:
                    return (r.mes("deathmessageLightning").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MAGMA_CUBE:
                    return (r.mes("deathmessageMagmaCube").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_CHEST:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_COMMAND:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_FURNACE:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_HOPPER:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_MOB_SPAWNER:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MINECART_TNT:
                    return (r.mes("deathmessageTNT").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case MUSHROOM_COW:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case OCELOT:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case PAINTING:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case PIG:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case PIG_ZOMBIE:
                    return (r.mes("deathmessageZombiePigMan").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case PLAYER:
                    Player k = (Player) ev.getDamager();
                    String name = ItemUtil.getTypeName(((Player) ev.getDamager()).getItemInHand());
                    if (k.getItemInHand().hasItemMeta() && k.getItemInHand().getItemMeta().hasDisplayName()) {
                        name = k.getItemInHand().getItemMeta().getDisplayName();
                    } else {
                        if (name.toLowerCase().startsWith("a") || name.toLowerCase().startsWith("e") || name.toLowerCase().startsWith("o") || name.toLowerCase().startsWith("u") || name.toLowerCase().startsWith("i")) {
                            name = "an " + name;
                        } else {
                            name = "a " + name;
                        }
                    }
                    name = ChatColor.stripColor(name);
                    return (r.mes("deathmessagePlayer").replaceAll("%Killed", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())).replaceAll("%Killer", ((Player) ev.getDamager()).getName()).replaceAll("%Weapon", name));

                case PRIMED_TNT:
                    return (r.mes("deathmessageTNT").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SHEEP:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SILVERFISH:
                    return (r.mes("deathmessageSilverfish").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SKELETON:
                    Skeleton skel = (Skeleton) ev.getDamager();
                    if (skel.getSkeletonType().equals(SkeletonType.NORMAL)) {
                        return (r.mes("deathmessageSkeleton").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    } else {
                        return (r.mes("deathmessageWitherSkeleton").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                    }

                case SLIME:
                    return (r.mes("deathmessageSlime").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SMALL_FIREBALL:
                    return (r.mes("deathmessageBlaze").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SNOWBALL:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SNOWMAN:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SPIDER:
                    return (r.mes("deathmessageSpider").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SPLASH_POTION:
                    return (r.mes("deathmessagePotion").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case SQUID:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case THROWN_EXP_BOTTLE:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case UNKNOWN:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case VILLAGER:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case WEATHER:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case WITCH:
                    return (r.mes("deathmessageWitch").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case WITHER:
                    return (r.mes("deathmessageWither").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case WITHER_SKULL:
                    return (r.mes("deathmessageWither").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case WOLF:
                    return (r.mes("deathmessageWolf").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));

                case ZOMBIE:
                    return (r.mes("deathmessageZombie").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                case ARMOR_STAND:
                    return (r.mes("deathmessageUnknown").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                case ENDERMITE:
                    return (r.mes("deathmessageEndermite").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                case GUARDIAN:
                    return (r.mes("deathmessageGuardian").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                case RABBIT:
                    return (r.mes("deathmessageRabbit").replaceAll("%Player", (UC.getPlayer(p).getNick() == null ? p.getDisplayName() : UC.getPlayer(p).getNick())));
                default:
                    break;
            }
        }
        return "FALSE";

        /*String d = e.getDeathMessage();
         Player p = e.getEntity();
         if (d.contains("was squashed by a falling anvil")) {
         e.setDeathMessage(r.mes("deathmessageAnvil", "%Player", p.getName()));
         } else if (d.contains("was pricked to death")) {
         e.setDeathMessage(r.mes("deathmessageCactusSelf", "%Player", p.getName()));
         } else if (d.contains("walked into a cactus whilst trying to escape")) {
         String t = d.split("escape ")[1];
         e.setDeathMessage(r.mes("deathmessageCactusOthers", "%Player", p.getName(), "%Target", t));
         } else if (d.contains("was shot by arrow")) {
         e.setDeathMessage(r.mes("deathmessageDispenser"));
         } else if (d.contains("drowned whilst trying to escape")) {
         String t = d.split("escape ")[1];
         e.setDeathMessage(r.mes("deathmessageDrownOthers", "%Target", t));
         } else if (d.contains("drowned")) {

         }*/
    }
    /*
     deathmessageAnvil=&6%Player &9was squashed by a falling anvil.
     deathmessageCactusSelf=&6%Player &9was pricked to death.
     deathmessageCactusOthers=&6%Player &9walked into a cactus while trying to escape &6%Target&9.
     deathmessageDispenser=&6%Player &9was shot by an arrow.
     deathmessageDrownSelf=&6%Player &9drowned.
     deathmessageDrownOthers=&6%Player &9drowned whilst trying to escape &6%Target&9.
     deathmessageExplosionSelf=&6%Player &9blew up.
     deathmessageExplosionOthers=&6%Player &9was blown up by &6%Target&9.
     deathmessageFallingShort=&6%Player &9hit the ground too hard.
     deathmessageFallingHigh=&6%Player &9fell from a high place.
     deathmessageFallingLadder=&6%Player &9fell off a ladder.
     deathmessageFallingVines=&6%Player &9fell off some vines.
     deathmessageFallingWater=&6%Player &9fell out of the water.
     deathmessageFallingFire=&6%Player &9fell into a patch of fire.
     deathmessageFallingCactus=&6%Player &9fell into a patch of cacti.
     deathmessageFallingOthers=&6%Player &9was doomed to fall.
     deathmessageFallingOthers2=&6%Player &9was doomed to fall by &6%Target&9.
     deathmessageFallingVinesOthers=&6%Player &9was shot off some vines by &6%Target&9.
     deathmessageFallingLaddersOthers=&6%Player &9was shot off a ladder by &6%Target&9.
     deathmessageFallingExplosion=&6%Player &9was blown from a high place by &6%Target&9.
     deathmessageFireSource=&6%Player &9went up in flames.
     deathmessageFireBurned=&6%Player &9burned to death.
     deathmessageFireCrisp=&6%Player &9was burnt to a crisp whilst fighting &6%Target&9.
     deathmessageFireWalked=&6%Player &9walked into a fire whilst fighting &6%Target&9.
     deathmessageMobSlain=&6%Player &9was slain by &6%Target&9.
     deathmessageMobShot=&6%Player &9was shot by &6%Target&9.
     deathmessageMobFireball=&6%Player &9was fireballed by &6%Target&9.
     deathmessageMobMagic=&6%Player &9was killed by &6%Target &9using magic.
     deathmessageMobWeapon=&6%Player &9got finished off by &6%Target &9using &6%Weapon&9.
     deathmessageMobRenamedWeapon=&6%Player &9was slain by &6%Target &9using &6%Weapon&9.
     deathmessageLavaSelf=&6%Player &9tried to swim in lava.
     deathmessageLavaOthers=&6%Player &9tried to swim in lava while trying to escape &6%Target&9.
     deathmessageOtherSuicide=&6%Player &9died.
     deathmessageOtherLightning=&6%Player &9was stuck by lightning.
     deathmessageOtherFallingBlock=&6%Player &9was squashed by a falling block.
     deathmessagePvp=&6%Player &9got finished off by &6%Target9.
     deathmessagePvpWeapon=&6%Player &9got finished off by &6%Target&9.
     deathmessagePvpShot=&6%Player &9was shot by &6%Target&9.
     deathmessagePvpMagic=&6%Player &9was killed by &6%Player &9using magic.
     deathmessageHarming=&6%Player &9was killed by magic.
     deathmessageStarvation=&6%Player &9starved to death.
     deathmessageSuffocation=&6%Player &9suffocated in a wall.
     deathmessageThorns=&6%Player &9was killed while trying to hurt &6%Target&9.
     deathmessagePummeled=&6%Player &9was pummeled by &6%Target&9.
     deathmessageVoid=&6%Player &9fell out of the world.
     deathmessageFallOut=&6%Player &9fell from a high place and fell out of the world.
     deathmessageKnocked=&6%Player &9was knocked into the void by &6%Target&9.
     deathmessageWither=&6%Player &9withered away.
     */
}