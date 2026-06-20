// Start of a method/block
module net.minestom.server {
    // Code statement
    requires transitive static org.jetbrains.annotations;  // TODO remove this when jspecify matures.
    // Code statement
    requires transitive com.google.gson;
    // Code statement
    requires it.unimi.dsi.fastutil;
    // Code statement
    requires space.vectrix.flare.fastutil;
    // Code statement
    requires transitive net.kyori.adventure.api;
    // Code statement
    requires transitive net.kyori.adventure.nbt;
    // Code statement
    requires net.kyori.adventure.text.logger.slf4j;
    // Code statement
    requires net.kyori.adventure.text.serializer.legacy;
    // Code statement
    requires net.kyori.adventure.text.serializer.gson;
    // Code statement
    requires net.kyori.adventure.text.serializer.plain;
    // Code statement
    requires net.kyori.adventure.text.serializer.json;
    // Code statement
    requires net.kyori.adventure.text.serializer.ansi;
    // Code statement
    requires org.slf4j;
    // Code statement
    requires org.jctools.core;
    // Code statement
    requires jdk.jfr;
    // Code statement
    requires java.desktop;
    // Code statement
    requires java.management;
    // Code statement
    requires net.minestom.data;
    // Code statement
    requires io.netty.buffer;
    // Code statement
    requires io.netty.codec;
    // Code statement
    requires io.netty.transport;
    // Code statement
    requires io.netty.transport.unix.common;
    // Code statement
    requires io.netty.transport.classes.epoll;
    // Code statement
    requires io.netty.common;

    // EXPORTS
    // Code statement
    exports net.minestom.server;
    // Code statement
    exports net.minestom.server.advancements;
    // Code statement
    exports net.minestom.server.adventure;
    // Code statement
    exports net.minestom.server.adventure.audience;
    // Code statement
    exports net.minestom.server.adventure.bossbar;
    // Code statement
    exports net.minestom.server.adventure.provider;
    // Code statement
    exports net.minestom.server.adventure.serializer.nbt;
    // Code statement
    exports net.minestom.server.codec;
    // Code statement
    exports net.minestom.server.collision;
    // Code statement
    exports net.minestom.server.color;
    // Code statement
    exports net.minestom.server.command;
    // Code statement
    exports net.minestom.server.command.builder;
    // Code statement
    exports net.minestom.server.command.builder.arguments;
    // Code statement
    exports net.minestom.server.command.builder.arguments.minecraft;
    // Code statement
    exports net.minestom.server.command.builder.arguments.minecraft.registry;
    // Code statement
    exports net.minestom.server.command.builder.arguments.number;
    // Code statement
    exports net.minestom.server.command.builder.arguments.relative;
    // Code statement
    exports net.minestom.server.command.builder.condition;
    // Code statement
    exports net.minestom.server.command.builder.exception;
    // Code statement
    exports net.minestom.server.command.builder.parser;
    // Code statement
    exports net.minestom.server.command.builder.suggestion;
    // Code statement
    exports net.minestom.server.component;
    // Code statement
    exports net.minestom.server.condition;
    // Code statement
    exports net.minestom.server.coordinate;
    // Code statement
    exports net.minestom.server.crypto;
    // Code statement
    exports net.minestom.server.dialog;
    // Code statement
    exports net.minestom.server.entity;
    // Code statement
    exports net.minestom.server.entity.ai;
    // Code statement
    exports net.minestom.server.entity.ai.goal;
    // Code statement
    exports net.minestom.server.entity.ai.target;
    // Code statement
    exports net.minestom.server.entity.attribute;
    // Code statement
    exports net.minestom.server.entity.damage;
    // Code statement
    exports net.minestom.server.entity.metadata;
    // Code statement
    exports net.minestom.server.entity.metadata.ambient;
    // Code statement
    exports net.minestom.server.entity.metadata.animal;
    // Code statement
    exports net.minestom.server.entity.metadata.animal.tameable;
    // Code statement
    exports net.minestom.server.entity.metadata.avatar;
    // Code statement
    exports net.minestom.server.entity.metadata.display;
    // Code statement
    exports net.minestom.server.entity.metadata.flying;
    // Code statement
    exports net.minestom.server.entity.metadata.golem;
    // Code statement
    exports net.minestom.server.entity.metadata.item;
    // Code statement
    exports net.minestom.server.entity.metadata.minecart;
    // Code statement
    exports net.minestom.server.entity.metadata.monster;
    // Code statement
    exports net.minestom.server.entity.metadata.monster.raider;
    // Code statement
    exports net.minestom.server.entity.metadata.monster.skeleton;
    // Code statement
    exports net.minestom.server.entity.metadata.monster.zombie;
    // Code statement
    exports net.minestom.server.entity.metadata.other;
    // Code statement
    exports net.minestom.server.entity.metadata.projectile;
    // Code statement
    exports net.minestom.server.entity.metadata.villager;
    // Code statement
    exports net.minestom.server.entity.metadata.water;
    // Code statement
    exports net.minestom.server.entity.metadata.water.fish;
    // Code statement
    exports net.minestom.server.entity.pathfinding;
    // Code statement
    exports net.minestom.server.entity.pathfinding.followers;
    // Code statement
    exports net.minestom.server.entity.pathfinding.generators;
    // Code statement
    exports net.minestom.server.entity.vehicle;
    // Code statement
    exports net.minestom.server.event;
    // Code statement
    exports net.minestom.server.event.book;
    // Code statement
    exports net.minestom.server.event.entity;
    // Code statement
    exports net.minestom.server.event.entity.projectile;
    // Code statement
    exports net.minestom.server.event.instance;
    // Code statement
    exports net.minestom.server.event.inventory;
    // Code statement
    exports net.minestom.server.event.item;
    // Code statement
    exports net.minestom.server.event.player;
    // Code statement
    exports net.minestom.server.event.server;
    // Code statement
    exports net.minestom.server.event.trait;
    // Code statement
    exports net.minestom.server.exception;
    // Code statement
    exports net.minestom.server.extras.lan;
    // Code statement
    exports net.minestom.server.extras.mojangAuth;
    // Code statement
    exports net.minestom.server.game;
    // Code statement
    exports net.minestom.server.gamedata;
    // Code statement
    exports net.minestom.server.instance;
    // Code statement
    exports net.minestom.server.instance.anvil;
    // Code statement
    exports net.minestom.server.instance.batch;
    // Code statement
    exports net.minestom.server.instance.block;
    // Code statement
    exports net.minestom.server.instance.block.banner;
    // Code statement
    exports net.minestom.server.instance.block.jukebox;
    // Code statement
    exports net.minestom.server.instance.block.predicate;
    // Code statement
    exports net.minestom.server.instance.block.rule;
    // Code statement
    exports net.minestom.server.instance.fluid;
    // Code statement
    exports net.minestom.server.instance.gamerule;
    // Code statement
    exports net.minestom.server.instance.generator;
    // Code statement
    exports net.minestom.server.instance.heightmap;
    // Code statement
    exports net.minestom.server.instance.light;
    // Code statement
    exports net.minestom.server.instance.palette;
    // Code statement
    exports net.minestom.server.inventory;
    // Code statement
    exports net.minestom.server.inventory.click;
    // Code statement
    exports net.minestom.server.inventory.type;
    // Code statement
    exports net.minestom.server.item;
    // Code statement
    exports net.minestom.server.item.armor;
    // Code statement
    exports net.minestom.server.item.book;
    // Code statement
    exports net.minestom.server.item.component;
    // Code statement
    exports net.minestom.server.item.crossbow;
    // Code statement
    exports net.minestom.server.item.enchant;
    // Code statement
    exports net.minestom.server.item.instrument;
    // Code statement
    exports net.minestom.server.listener;
    // Code statement
    exports net.minestom.server.listener.common;
    // Code statement
    exports net.minestom.server.listener.manager;
    // Code statement
    exports net.minestom.server.listener.preplay;
    // Code statement
    exports net.minestom.server.map;
    // Code statement
    exports net.minestom.server.map.framebuffers;
    // Code statement
    exports net.minestom.server.message;
    // Code statement
    exports net.minestom.server.monitoring;
    // Code statement
    exports net.minestom.server.network;
    // Code statement
    exports net.minestom.server.network.debug;
    // Code statement
    exports net.minestom.server.network.debug.info;
    // Code statement
    exports net.minestom.server.network.packet;
    // Code statement
    exports net.minestom.server.network.packet.client;
    // Code statement
    exports net.minestom.server.network.packet.client.common;
    // Code statement
    exports net.minestom.server.network.packet.client.configuration;
    // Code statement
    exports net.minestom.server.network.packet.client.handshake;
    // Code statement
    exports net.minestom.server.network.packet.client.login;
    // Code statement
    exports net.minestom.server.network.packet.client.play;
    // Code statement
    exports net.minestom.server.network.packet.client.status;
    // Code statement
    exports net.minestom.server.network.packet.server;
    // Code statement
    exports net.minestom.server.network.packet.server.common;
    // Code statement
    exports net.minestom.server.network.packet.server.configuration;
    // Code statement
    exports net.minestom.server.network.packet.server.login;
    // Code statement
    exports net.minestom.server.network.packet.server.play;
    // Code statement
    exports net.minestom.server.network.packet.server.play.data;
    // Code statement
    exports net.minestom.server.network.packet.server.status;
    // Code statement
    exports net.minestom.server.network.player;
    // Code statement
    exports net.minestom.server.network.plugin;
    // Code statement
    exports net.minestom.server.network.socket;
    // Code statement
    exports net.minestom.server.particle;
    // Code statement
    exports net.minestom.server.ping;
    // Code statement
    exports net.minestom.server.potion;
    // Code statement
    exports net.minestom.server.recipe;
    // Code statement
    exports net.minestom.server.recipe.display;
    // Code statement
    exports net.minestom.server.registry;
    // Code statement
    exports net.minestom.server.scoreboard;
    // Code statement
    exports net.minestom.server.snapshot;
    // Code statement
    exports net.minestom.server.sound;
    // Code statement
    exports net.minestom.server.statistic;
    // Code statement
    exports net.minestom.server.tag;
    // Code statement
    exports net.minestom.server.thread;
    // Code statement
    exports net.minestom.server.timer;
    // Code statement
    exports net.minestom.server.utils;
    // Code statement
    exports net.minestom.server.utils.async;
    // Code statement
    exports net.minestom.server.utils.block;
    // Code statement
    exports net.minestom.server.utils.callback;
    // Code statement
    exports net.minestom.server.utils.chunk;
    // Code statement
    exports net.minestom.server.utils.collection;
    // Code statement
    exports net.minestom.server.utils.crypto;
    // Code statement
    exports net.minestom.server.utils.entity;
    // Code statement
    exports net.minestom.server.utils.identity;
    // Code statement
    exports net.minestom.server.utils.inventory;
    // Code statement
    exports net.minestom.server.utils.json;
    // Code statement
    exports net.minestom.server.utils.location;
    // Code statement
    exports net.minestom.server.utils.mojang;
    // Code statement
    exports net.minestom.server.utils.nbt;
    // Code statement
    exports net.minestom.server.utils.position;
    // Code statement
    exports net.minestom.server.utils.time;
    // Code statement
    exports net.minestom.server.utils.url;
    // Code statement
    exports net.minestom.server.utils.validate;
    // Code statement
    exports net.minestom.server.world;
    // Code statement
    exports net.minestom.server.worldevent;
    // Code statement
    exports net.minestom.server.world.attribute;
    // Code statement
    exports net.minestom.server.world.biome;
    // Code statement
    exports net.minestom.server.world.timeline;
    // Code statement
    exports net.minestom.server.world.clock;

    // Code statement
    provides net.kyori.adventure.text.logger.slf4j.ComponentLoggerProvider with net.minestom.server.adventure.provider.MinestomComponentLoggerProvider;
    // Code statement
    provides net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer.Provider with net.minestom.server.adventure.provider.MinestomAnsiComponentSerializerProvider;
    // Code statement
    provides net.kyori.adventure.text.event.ClickCallback.Provider with net.minestom.server.adventure.provider.MinestomClickCallbackProvider;
    // Code statement
    provides net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.Provider with net.minestom.server.adventure.provider.MinestomPlainTextComponentSerializerProvider;
    // Code statement
    provides net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Provider with net.minestom.server.adventure.provider.MinestomLegacyComponentSerializerProvider;
    // Code statement
    provides net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.Provider with net.minestom.server.adventure.provider.MinestomGsonComponentSerializerProvider;
    // Code statement
    provides net.kyori.adventure.text.event.DataComponentValueConverterRegistry.Provider with net.minestom.server.adventure.provider.MinestomDataComponentValueConverterProvider;
// End of a block/expression
}