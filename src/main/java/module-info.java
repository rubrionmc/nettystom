// Début d'une méthode/d'un bloc
module net.minestom.server {
    // Instruction de code
    requires transitive static org.jetbrains.annotations;  // TODO remove this when jspecify matures.
    // Instruction de code
    requires transitive com.google.gson;
    // Instruction de code
    requires it.unimi.dsi.fastutil;
    // Instruction de code
    requires space.vectrix.flare.fastutil;
    // Instruction de code
    requires jdk.unsupported; // Unsafe
    // Instruction de code
    requires transitive net.kyori.adventure;
    // Instruction de code
    requires transitive net.kyori.adventure.nbt;
    // Instruction de code
    requires transitive net.kyori.adventure.key;
    // Instruction de code
    requires transitive net.kyori.examination.api;
    // Instruction de code
    requires net.kyori.adventure.text.logger.slf4j;
    // Instruction de code
    requires net.kyori.adventure.text.serializer.legacy;
    // Instruction de code
    requires net.kyori.adventure.text.serializer.gson;
    // Instruction de code
    requires net.kyori.adventure.text.serializer.plain;
    // Instruction de code
    requires net.kyori.adventure.text.serializer.json;
    // Instruction de code
    requires net.kyori.adventure.text.serializer.ansi;
    // Instruction de code
    requires org.slf4j;
    // Instruction de code
    requires org.jctools.core;
    // Instruction de code
    requires jdk.jfr;
    // Instruction de code
    requires java.desktop;
    // Instruction de code
    requires java.management;
    // Instruction de code
    requires io.netty.buffer;
    // Instruction de code
    requires io.netty.codec;
    // Instruction de code
    requires io.netty.transport;
    // Instruction de code
    requires io.netty.transport.unix.common;
    // Instruction de code
    requires io.netty.transport.classes.epoll;
    // Instruction de code
    requires io.netty.common;

    // EXPORTS
    // Instruction de code
    exports net.minestom.server;
    // Instruction de code
    exports net.minestom.server.advancements;
    // Instruction de code
    exports net.minestom.server.adventure;
    // Instruction de code
    exports net.minestom.server.adventure.audience;
    // Instruction de code
    exports net.minestom.server.adventure.bossbar;
    // Instruction de code
    exports net.minestom.server.adventure.provider;
    // Instruction de code
    exports net.minestom.server.adventure.serializer.nbt;
    // Instruction de code
    exports net.minestom.server.codec;
    // Instruction de code
    exports net.minestom.server.collision;
    // Instruction de code
    exports net.minestom.server.color;
    // Instruction de code
    exports net.minestom.server.command;
    // Instruction de code
    exports net.minestom.server.command.builder;
    // Instruction de code
    exports net.minestom.server.command.builder.arguments;
    // Instruction de code
    exports net.minestom.server.command.builder.arguments.minecraft;
    // Instruction de code
    exports net.minestom.server.command.builder.arguments.minecraft.registry;
    // Instruction de code
    exports net.minestom.server.command.builder.arguments.number;
    // Instruction de code
    exports net.minestom.server.command.builder.arguments.relative;
    // Instruction de code
    exports net.minestom.server.command.builder.condition;
    // Instruction de code
    exports net.minestom.server.command.builder.exception;
    // Instruction de code
    exports net.minestom.server.command.builder.parser;
    // Instruction de code
    exports net.minestom.server.command.builder.suggestion;
    // Instruction de code
    exports net.minestom.server.component;
    // Instruction de code
    exports net.minestom.server.condition;
    // Instruction de code
    exports net.minestom.server.coordinate;
    // Instruction de code
    exports net.minestom.server.crypto;
    // Instruction de code
    exports net.minestom.server.dialog;
    // Instruction de code
    exports net.minestom.server.entity;
    // Instruction de code
    exports net.minestom.server.entity.ai;
    // Instruction de code
    exports net.minestom.server.entity.ai.goal;
    // Instruction de code
    exports net.minestom.server.entity.ai.target;
    // Instruction de code
    exports net.minestom.server.entity.attribute;
    // Instruction de code
    exports net.minestom.server.entity.damage;
    // Instruction de code
    exports net.minestom.server.entity.metadata;
    // Instruction de code
    exports net.minestom.server.entity.metadata.ambient;
    // Instruction de code
    exports net.minestom.server.entity.metadata.animal;
    // Instruction de code
    exports net.minestom.server.entity.metadata.animal.tameable;
    // Instruction de code
    exports net.minestom.server.entity.metadata.avatar;
    // Instruction de code
    exports net.minestom.server.entity.metadata.display;
    // Instruction de code
    exports net.minestom.server.entity.metadata.flying;
    // Instruction de code
    exports net.minestom.server.entity.metadata.golem;
    // Instruction de code
    exports net.minestom.server.entity.metadata.item;
    // Instruction de code
    exports net.minestom.server.entity.metadata.minecart;
    // Instruction de code
    exports net.minestom.server.entity.metadata.monster;
    // Instruction de code
    exports net.minestom.server.entity.metadata.monster.raider;
    // Instruction de code
    exports net.minestom.server.entity.metadata.monster.skeleton;
    // Instruction de code
    exports net.minestom.server.entity.metadata.monster.zombie;
    // Instruction de code
    exports net.minestom.server.entity.metadata.other;
    // Instruction de code
    exports net.minestom.server.entity.metadata.projectile;
    // Instruction de code
    exports net.minestom.server.entity.metadata.villager;
    // Instruction de code
    exports net.minestom.server.entity.metadata.water;
    // Instruction de code
    exports net.minestom.server.entity.metadata.water.fish;
    // Instruction de code
    exports net.minestom.server.entity.pathfinding;
    // Instruction de code
    exports net.minestom.server.entity.pathfinding.followers;
    // Instruction de code
    exports net.minestom.server.entity.pathfinding.generators;
    // Instruction de code
    exports net.minestom.server.entity.vehicle;
    // Instruction de code
    exports net.minestom.server.event;
    // Instruction de code
    exports net.minestom.server.event.book;
    // Instruction de code
    exports net.minestom.server.event.entity;
    // Instruction de code
    exports net.minestom.server.event.entity.projectile;
    // Instruction de code
    exports net.minestom.server.event.instance;
    // Instruction de code
    exports net.minestom.server.event.inventory;
    // Instruction de code
    exports net.minestom.server.event.item;
    // Instruction de code
    exports net.minestom.server.event.player;
    // Instruction de code
    exports net.minestom.server.event.server;
    // Instruction de code
    exports net.minestom.server.event.trait;
    // Instruction de code
    exports net.minestom.server.exception;
    // Instruction de code
    exports net.minestom.server.extras.lan;
    // Instruction de code
    exports net.minestom.server.extras.mojangAuth;
    // Instruction de code
    exports net.minestom.server.extras.query;
    // Instruction de code
    exports net.minestom.server.extras.query.event;
    // Instruction de code
    exports net.minestom.server.extras.query.response;
    // Instruction de code
    exports net.minestom.server.game;
    // Instruction de code
    exports net.minestom.server.gamedata;
    // Instruction de code
    exports net.minestom.server.instance;
    // Instruction de code
    exports net.minestom.server.instance.anvil;
    // Instruction de code
    exports net.minestom.server.instance.batch;
    // Instruction de code
    exports net.minestom.server.instance.block;
    // Instruction de code
    exports net.minestom.server.instance.block.banner;
    // Instruction de code
    exports net.minestom.server.instance.block.jukebox;
    // Instruction de code
    exports net.minestom.server.instance.block.predicate;
    // Instruction de code
    exports net.minestom.server.instance.block.rule;
    // Instruction de code
    exports net.minestom.server.instance.fluid;
    // Instruction de code
    exports net.minestom.server.instance.generator;
    // Instruction de code
    exports net.minestom.server.instance.heightmap;
    // Instruction de code
    exports net.minestom.server.instance.light;
    // Instruction de code
    exports net.minestom.server.instance.palette;
    // Instruction de code
    exports net.minestom.server.inventory;
    // Instruction de code
    exports net.minestom.server.inventory.click;
    // Instruction de code
    exports net.minestom.server.inventory.type;
    // Instruction de code
    exports net.minestom.server.item;
    // Instruction de code
    exports net.minestom.server.item.armor;
    // Instruction de code
    exports net.minestom.server.item.book;
    // Instruction de code
    exports net.minestom.server.item.component;
    // Instruction de code
    exports net.minestom.server.item.crossbow;
    // Instruction de code
    exports net.minestom.server.item.enchant;
    // Instruction de code
    exports net.minestom.server.item.instrument;
    // Instruction de code
    exports net.minestom.server.listener;
    // Instruction de code
    exports net.minestom.server.listener.common;
    // Instruction de code
    exports net.minestom.server.listener.manager;
    // Instruction de code
    exports net.minestom.server.listener.preplay;
    // Instruction de code
    exports net.minestom.server.map;
    // Instruction de code
    exports net.minestom.server.map.framebuffers;
    // Instruction de code
    exports net.minestom.server.message;
    // Instruction de code
    exports net.minestom.server.monitoring;
    // Instruction de code
    exports net.minestom.server.network;
    // Instruction de code
    exports net.minestom.server.network.debug;
    // Instruction de code
    exports net.minestom.server.network.debug.info;
    // Instruction de code
    exports net.minestom.server.network.packet;
    // Instruction de code
    exports net.minestom.server.network.packet.client;
    // Instruction de code
    exports net.minestom.server.network.packet.client.common;
    // Instruction de code
    exports net.minestom.server.network.packet.client.configuration;
    // Instruction de code
    exports net.minestom.server.network.packet.client.handshake;
    // Instruction de code
    exports net.minestom.server.network.packet.client.login;
    // Instruction de code
    exports net.minestom.server.network.packet.client.play;
    // Instruction de code
    exports net.minestom.server.network.packet.client.status;
    // Instruction de code
    exports net.minestom.server.network.packet.server;
    // Instruction de code
    exports net.minestom.server.network.packet.server.common;
    // Instruction de code
    exports net.minestom.server.network.packet.server.configuration;
    // Instruction de code
    exports net.minestom.server.network.packet.server.login;
    // Instruction de code
    exports net.minestom.server.network.packet.server.play;
    // Instruction de code
    exports net.minestom.server.network.packet.server.play.data;
    // Instruction de code
    exports net.minestom.server.network.packet.server.status;
    // Instruction de code
    exports net.minestom.server.network.player;
    // Instruction de code
    exports net.minestom.server.network.plugin;
    // Instruction de code
    exports net.minestom.server.network.socket;
    // Instruction de code
    exports net.minestom.server.particle;
    // Instruction de code
    exports net.minestom.server.ping;
    // Instruction de code
    exports net.minestom.server.potion;
    // Instruction de code
    exports net.minestom.server.recipe;
    // Instruction de code
    exports net.minestom.server.recipe.display;
    // Instruction de code
    exports net.minestom.server.registry;
    // Instruction de code
    exports net.minestom.server.scoreboard;
    // Instruction de code
    exports net.minestom.server.snapshot;
    // Instruction de code
    exports net.minestom.server.sound;
    // Instruction de code
    exports net.minestom.server.statistic;
    // Instruction de code
    exports net.minestom.server.tag;
    // Instruction de code
    exports net.minestom.server.thread;
    // Instruction de code
    exports net.minestom.server.timer;
    // Instruction de code
    exports net.minestom.server.utils;
    // Instruction de code
    exports net.minestom.server.utils.async;
    // Instruction de code
    exports net.minestom.server.utils.block;
    // Instruction de code
    exports net.minestom.server.utils.callback;
    // Instruction de code
    exports net.minestom.server.utils.chunk;
    // Instruction de code
    exports net.minestom.server.utils.collection;
    // Instruction de code
    exports net.minestom.server.utils.crypto;
    // Instruction de code
    exports net.minestom.server.utils.entity;
    // Instruction de code
    exports net.minestom.server.utils.identity;
    // Instruction de code
    exports net.minestom.server.utils.inventory;
    // Instruction de code
    exports net.minestom.server.utils.json;
    // Instruction de code
    exports net.minestom.server.utils.location;
    // Instruction de code
    exports net.minestom.server.utils.mojang;
    // Instruction de code
    exports net.minestom.server.utils.nbt;
    // Instruction de code
    exports net.minestom.server.utils.position;
    // Instruction de code
    exports net.minestom.server.utils.time;
    // Instruction de code
    exports net.minestom.server.utils.url;
    // Instruction de code
    exports net.minestom.server.utils.validate;
    // Instruction de code
    exports net.minestom.server.world;
    // Instruction de code
    exports net.minestom.server.world.attribute;
    // Instruction de code
    exports net.minestom.server.world.biome;
    // Instruction de code
    exports net.minestom.server.world.timeline;

// Fin d'un bloc/d'une expression
}