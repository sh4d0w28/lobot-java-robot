package com.lobot.commands.domain;

public class BotCommand {
    private BotCommandEnum command;
    private Integer value;
    private Long delayMs;

    public BotCommand(BotCommandEnum command, Integer value, Long delayMs) {
        this.command = command;
        this.value = value;
        this.delayMs = delayMs;
    }

    public BotCommandEnum getCommand() {
        return command;
    }

    public Integer getValue() {
        return value;
    }

    public Long getDelayMs() {
        return delayMs;
    }
}
