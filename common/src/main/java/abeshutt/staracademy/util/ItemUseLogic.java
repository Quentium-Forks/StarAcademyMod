package abeshutt.staracademy.util;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.item.ItemPredicate;
import abeshutt.staracademy.data.serializable.ISerializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static abeshutt.staracademy.data.adapter.basic.EnumAdapter.Mode.NAME;

public class ItemUseLogic implements ISerializable<NbtCompound, JsonObject> {

    private ItemPredicate predicate;
    private final List<String> commands;
    private CommandExecutionContext context;

    public ItemUseLogic() {
        this.commands = new ArrayList<>();
    }

    public ItemUseLogic(String predicate, CommandExecutionContext context, String... commands) {
        this.predicate = ItemPredicate.of(predicate, true).orElseThrow();
        this.context = context;
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public ItemPredicate getPredicate() {
        return this.predicate;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public CommandExecutionContext getContext() {
        return this.context;
    }

    @Override
    public Optional<JsonObject> writeJson() {
        return Optional.of(new JsonObject()).map(json -> {
            Adapters.ITEM_PREDICATE.writeJson(this.predicate).ifPresent(tag -> json.add("predicate", tag));

            JsonArray commands = new JsonArray();
            this.commands.forEach(command -> Adapters.UTF_8.writeJson(command).ifPresent(commands::add));
            json.add("commands", commands);
            Adapters.ofEnum(CommandExecutionContext.class, NAME).writeJson(this.context)
                    .ifPresent(tag -> json.add("context", tag));
            return json;
        });
    }

    @Override
    public void readJson(JsonObject json) {
        this.predicate = Adapters.ITEM_PREDICATE.readJson(json.get("predicate")).orElse(ItemPredicate.FALSE);
        this.commands.clear();

        if(json.get("commands") instanceof JsonArray array) {
            for(JsonElement element : array) {
               Adapters.UTF_8.readJson(element).ifPresent(this.commands::add);
            }
        }

        this.context = Adapters.ofEnum(CommandExecutionContext.class, NAME).readJson(json.get("context")).orElse(CommandExecutionContext.SERVER);
    }

    public enum CommandExecutionContext {
        PLAYER, SERVER
    }

}
