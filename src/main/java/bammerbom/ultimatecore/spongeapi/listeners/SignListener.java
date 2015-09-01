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
package bammerbom.ultimatecore.spongeapi.listeners;

import bammerbom.ultimatecore.spongeapi.UltimateSign;
import bammerbom.ultimatecore.spongeapi.UltimateSigns;
import bammerbom.ultimatecore.spongeapi.r;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.tileentity.SignChangeEvent;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.text.Text;

public class SignListener {

    public static void start() {
        r.getGame().getEventManager().register(r.getUC(), new SignListener());
    }

    @Subscribe(order = Order.LATE)
    public void onSignChange(SignChangeEvent e) {
        //Signs
        Player p;
        if (e.getCause().isPresent() && e.getCause().get().getCause() instanceof Player) {
            p = (Player) e.getCause().get().getCause();
        } else {
            return;
        }
        for (UltimateSign s : UltimateSigns.signs) {
            if (r.stripColor(((Text.Literal) e.getNewData().lines().get(0)).getContent()).equalsIgnoreCase("[" + s.getName() + "]")) {
                s.onCreate(e, p);
            }
        }
        //Color signs
        if (!r.perm(p, "uc.sign.colored", false, false)) {
            return;
        }
        e.setNewData(e.getNewData().set(Keys.SIGN_LINES, e.getNewData().lines().set(0, r.translateAlternateColorCodes('&', e.getNewData().lines().get(0))).get()));
        e.setNewData(e.getNewData().set(Keys.SIGN_LINES, e.getNewData().lines().set(1, r.translateAlternateColorCodes('&', e.getNewData().lines().get(1))).get()));
        e.setNewData(e.getNewData().set(Keys.SIGN_LINES, e.getNewData().lines().set(2, r.translateAlternateColorCodes('&', e.getNewData().lines().get(2))).get()));
        e.setNewData(e.getNewData().set(Keys.SIGN_LINES, e.getNewData().lines().set(3, r.translateAlternateColorCodes('&', e.getNewData().lines().get(3))).get()));
    }

    @Subscribe(order = Order.LATE)
    public void onSignInteract(PlayerInteractBlockEvent e) {
        //Signs
        if (!e.getInteractionType().equals(EntityInteractionTypes.USE)) {
            return;
        }
        if (e.getLocation() == null || e.getLocation().getBlockType().equals(BlockTypes.AIR)) {
            return;
        }
        if (!e.getLocation().getTileEntity().isPresent() || !(e.getLocation().getTileEntity().get() instanceof Sign)) {
            return;
        }
        Sign sign = (Sign) e.getLocation().getTileEntity().get();
        for (UltimateSign s : UltimateSigns.signs) {
            if (r.stripColor(((Text.Literal) sign.getData().get().lines().get(0)).getContent()).equalsIgnoreCase("[" + s.getName() + "]")) {
                s.onClick(e.getUser(), sign);
            }
        }
    }

    @Subscribe(order = Order.LATE)
    public void onSignDestroy(PlayerBreakBlockEvent e) {
        //Signs
        if (e.getLocation() == null || e.getLocation().getBlockType().equals(BlockTypes.AIR)) {
            return;
        }
        if (!e.getLocation().getTileEntity().isPresent() || !(e.getLocation().getTileEntity().get() instanceof Sign)) {
            return;
        }
        Sign sign = (Sign) e.getLocation().getTileEntity().get();
        for (UltimateSign s : UltimateSigns.signs) {
            if (r.stripColor(((Text.Literal) sign.getData().get().lines().get(0)).getContent()).equalsIgnoreCase("[" + s.getName() + "]")) {
                s.onDestroy(e);
            }
        }
    }

}
