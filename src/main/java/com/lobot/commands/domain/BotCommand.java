package com.lobot.commands.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BotCommand {
    @Getter private CmdEnum command;
    @Getter private Integer value;
    @Getter private Long delayMs;
}
