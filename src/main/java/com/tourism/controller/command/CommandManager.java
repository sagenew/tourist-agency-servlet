package com.tourism.controller.command;

import com.tourism.controller.command.auth.LoginCommand;
import com.tourism.controller.command.auth.LogoutCommand;
import com.tourism.controller.command.auth.RegistrationCommand;
import com.tourism.controller.command.home.HomeCommand;
import com.tourism.controller.command.order.*;
import com.tourism.controller.command.tour.*;
import com.tourism.controller.command.user.*;
import com.tourism.model.service.OrderService;
import com.tourism.model.service.TourService;
import com.tourism.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static CommandManager commandManager;

    private final Map<String, Command> commandMap = new HashMap<>();

    private CommandManager() {
        final UserService userService = new UserService();
        final TourService tourService = new TourService();
        final OrderService orderService = new OrderService();

        commandMap.put("/index", new HomeCommand());
        commandMap.put("/login", new LoginCommand(userService));
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/registration", new RegistrationCommand(userService));

        commandMap.put("/users", new UsersCommand(userService));
        commandMap.put("/users/update", new UserUpdateCommand(userService));
        commandMap.put("/users/delete", new UserDeleteCommand(userService));
        commandMap.put("/users/ban", new UserBanCommand(userService));
        commandMap.put("/users/unban", new UserUnbanCommand(userService));
        commandMap.put("/profile", new UserProfileCommand(userService));

        commandMap.put("/tours", new ToursCommand(tourService));
        commandMap.put("/tours/add", new TourAddCommand(tourService));
        commandMap.put("/tours/update", new TourUpdateCommand(tourService));
        commandMap.put("/tours/delete", new TourDeleteCommand(tourService));
        commandMap.put("/tours/mark-hot", new TourMarkAsHotCommand(tourService));

        commandMap.put("/tours/orders", new OrdersCommand(orderService));
        commandMap.put("/tours/orders/add", new OrderAddCommand(orderService));
        commandMap.put("/tours/orders/delete", new OrderDeleteCommand(orderService));
        commandMap.put("/tours/orders/mark-paid", new OrderMarkAsPaidCommand(orderService));
        commandMap.put("/tours/orders/mark-denied", new OrderMarkAsDeniedCommand(orderService));
        commandMap.put("/tours/orders/set-discount", new DiscountSetCommand(orderService));
    }

    public static CommandManager getInstance() {
        if (commandManager == null) {
            synchronized (CommandManager.class) {
                if (commandManager == null) {
                    commandManager = new CommandManager();
                }
            }
        }
        return commandManager;
    }

    public Command getCommand(String commandName) {
        return commandMap.getOrDefault(commandName, r -> "/index.jsp");
    }
}
