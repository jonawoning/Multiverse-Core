/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2020.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.onarandombox.MultiverseCore.commandTools;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.CommandContexts;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.PaperCommandManager;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.commands.AnchorCommand;
import com.onarandombox.MultiverseCore.commands.BedCommand;
import com.onarandombox.MultiverseCore.commands.CheckCommand;
import com.onarandombox.MultiverseCore.commands.CloneCommand;
import com.onarandombox.MultiverseCore.commands.ConfigCommand;
import com.onarandombox.MultiverseCore.commands.ConfirmCommand;
import com.onarandombox.MultiverseCore.commands.CoordCommand;
import com.onarandombox.MultiverseCore.commands.CreateCommand;
import com.onarandombox.MultiverseCore.commands.DebugCommand;
import com.onarandombox.MultiverseCore.commands.DeleteCommand;
import com.onarandombox.MultiverseCore.commands.EnvironmentCommand;
import com.onarandombox.MultiverseCore.commands.GameRuleCommand;
import com.onarandombox.MultiverseCore.commands.GeneratorCommand;
import com.onarandombox.MultiverseCore.commands.ImportCommand;
import com.onarandombox.MultiverseCore.commands.InfoCommand;
import com.onarandombox.MultiverseCore.commands.ListCommand;
import com.onarandombox.MultiverseCore.commands.LoadCommand;
import com.onarandombox.MultiverseCore.commands.ModifyCommand;
import com.onarandombox.MultiverseCore.commands.PurgeCommand;
import com.onarandombox.MultiverseCore.commands.RegenCommand;
import com.onarandombox.MultiverseCore.commands.ReloadCommand;
import com.onarandombox.MultiverseCore.commands.RemoveCommand;
import com.onarandombox.MultiverseCore.commands.ScriptCommand;
import com.onarandombox.MultiverseCore.commands.SetSpawnCommand;
import com.onarandombox.MultiverseCore.commands.SilentCommand;
import com.onarandombox.MultiverseCore.commands.SpawnCommand;
import com.onarandombox.MultiverseCore.commands.TeleportCommand;
import com.onarandombox.MultiverseCore.commands.UnloadCommand;
import com.onarandombox.MultiverseCore.commands.UsageCommand;
import com.onarandombox.MultiverseCore.commands.VersionCommand;
import com.onarandombox.MultiverseCore.commands.WhoCommand;

import java.util.Arrays;
import java.util.Set;

public class MVCommandManager extends PaperCommandManager {

    private final MultiverseCore plugin;
    private final CommandQueueManager commandQueueManager;

    public MVCommandManager(MultiverseCore plugin) {
        super(plugin);
        this.plugin = plugin;
        this.commandQueueManager = new CommandQueueManager(plugin);
        new MVCommandConditions(plugin, getCommandConditions());

        enableUnstableAPI("help");

        registerCommand(new UsageCommand(this.plugin));
        registerCommand(new CreateCommand(this.plugin));
        registerCommand(new LoadCommand(this.plugin));
        registerCommand(new UnloadCommand(this.plugin));
        registerCommand(new InfoCommand(this.plugin));
        registerCommand(new DeleteCommand(this.plugin));
        registerCommand(new ConfirmCommand(this.plugin));
        registerCommand(new ConfigCommand(this.plugin));
        registerCommand(new DebugCommand(this.plugin));
        registerCommand(new CoordCommand(this.plugin));
        registerCommand(new SpawnCommand(this.plugin));
        registerCommand(new ReloadCommand(this.plugin));
        registerCommand(new RemoveCommand(this.plugin));
        registerCommand(new ListCommand(this.plugin));
        registerCommand(new ScriptCommand(this.plugin));
        registerCommand(new GeneratorCommand(this.plugin));
        registerCommand(new CloneCommand(this.plugin));
        registerCommand(new ImportCommand(this.plugin));
        registerCommand(new CheckCommand(this.plugin));
        registerCommand(new GameRuleCommand(this.plugin));
        registerCommand(new EnvironmentCommand(this.plugin));
        registerCommand(new RegenCommand(this.plugin));
        registerCommand(new TeleportCommand(this.plugin));
        registerCommand(new SilentCommand(this.plugin));
        registerCommand(new PurgeCommand(this.plugin));
        registerCommand(new SetSpawnCommand(this.plugin));
        registerCommand(new ModifyCommand(this.plugin));
        registerCommand(new VersionCommand(this.plugin));
        registerCommand(new BedCommand(this.plugin));
        registerCommand(new AnchorCommand(this.plugin));
        registerCommand(new WhoCommand(this.plugin));
    }

    @Override
    public synchronized CommandContexts<BukkitCommandExecutionContext> getCommandContexts() {
        if (this.contexts == null) {
            this.contexts = new MVCommandContexts(this, plugin);
        }
        return this.contexts;
    }

    @Override
    public synchronized CommandCompletions<BukkitCommandCompletionContext> getCommandCompletions() {
        if (this.completions == null) {
            this.completions = new MVCommandCompletions(this, plugin);
        }
        return this.completions;
    }

    /**
     * Change default implementation to OR instead of AND
     */
    @Override
    public boolean hasPermission(CommandIssuer issuer, Set<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return true;
        }

        return permissions.stream()
                .anyMatch(permission -> hasPermission(issuer, permission));
    }

    /**
     * Change default implementation to OR instead of AND
     */
    @Override
    public boolean hasPermission(CommandIssuer issuer, String permission) {
        if (permission == null || permission.isEmpty()) {
            return true;
        }

        return Arrays.stream(permission.split(","))
                .anyMatch(perm -> !perm.isEmpty() && issuer.hasPermission(perm));
    }

    public CommandQueueManager getQueueManager() {
        return commandQueueManager;
    }
}
