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
package bammerbom.ultimatecore.sponge.modules.deaf.commands;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.command.Arguments;
import bammerbom.ultimatecore.sponge.api.command.RegisterCommand;
import bammerbom.ultimatecore.sponge.api.command.SmartCommand;
import bammerbom.ultimatecore.sponge.api.command.arguments.PlayerArgument;
import bammerbom.ultimatecore.sponge.api.command.arguments.TimeArgument;
import bammerbom.ultimatecore.sponge.api.permission.Permission;
import bammerbom.ultimatecore.sponge.api.user.UltimateUser;
import bammerbom.ultimatecore.sponge.modules.deaf.DeafModule;
import bammerbom.ultimatecore.sponge.modules.deaf.api.Deaf;
import bammerbom.ultimatecore.sponge.modules.deaf.api.DeafKeys;
import bammerbom.ultimatecore.sponge.modules.deaf.api.DeafPermissions;
import bammerbom.ultimatecore.sponge.utils.Messages;
import bammerbom.ultimatecore.sponge.utils.TimeUtil;
import bammerbom.ultimatecore.sponge.utils.VariableUtil;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RegisterCommand(module = DeafModule.class, aliases = {"deaf"})
public class DeafCommand implements SmartCommand {

    @Override
    public Permission getPermission() {
        return DeafPermissions.UC_DEAF_DEAF_BASE;
    }

    @Override
    public List<Permission> getPermissions() {
        return Arrays.asList(DeafPermissions.UC_DEAF_DEAF_BASE);
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                Arguments.builder(new PlayerArgument(Text.of("player"))).onlyOne().build(),
                Arguments.builder(new TimeArgument(Text.of("time"))).optionalWeak().onlyOne().build(),
                Arguments.builder(GenericArguments.remainingJoinedStrings(Text.of("reason"))).optionalWeak().onlyOne().build()
        };
    }

    @Override
    public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
        checkPermission(sender, DeafPermissions.UC_DEAF_DEAF_BASE);
        Player t = args.<Player>getOne("player").get();
        Long time = args.hasAny("time") ? args.<Long>getOne("time").get() : -1L;
        Text reason = args.hasAny("reason") ? Text.of(args.<String>getOne("reason").get()) : Messages.getFormatted("deaf.command.deaf.defaultreason");

        Long endtime = time == -1L ? -1L : System.currentTimeMillis() + time;
        Long starttime = System.currentTimeMillis();
        UUID deafr = sender instanceof Player ? ((Player) sender).getUniqueId() : UUID.fromString("00000000-0000-0000-0000-000000000000");
        UUID deafd = t.getUniqueId();

        Deaf deaf = new Deaf(deafd, deafr, endtime, starttime, reason);
        UltimateUser ut = UltimateCore.get().getUserService().getUser(t);
        ut.offer(DeafKeys.DEAF, deaf);

        sender.sendMessage(Messages.getFormatted(sender, "deaf.command.deaf.success", "%player%", VariableUtil.getNameEntity(t), "%time%", (time == -1L ? Messages.getFormatted("core.time.ever") : TimeUtil.format(time)), "%reason%", reason));
        t.sendMessage(Messages.getFormatted(t, "deaf.deafed", "%time%", (time == -1L ? Messages.getFormatted("core.time.ever") : TimeUtil.format(time)), "%reason%", reason));
        return CommandResult.success();
    }
}
