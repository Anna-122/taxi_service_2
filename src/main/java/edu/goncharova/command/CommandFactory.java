package edu.goncharova.command;

import edu.goncharova.admin.command.*;
import edu.goncharova.admin.command.LoginCommand;
import edu.goncharova.admin.command.LogoutCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final static CommandFactory factory = new CommandFactory();
    public final static String LOGIN = "login";
    public final static String REGISTRATION = "registration";
    public final static String LOGOUT = "logout";
    public final static String USERS = "users";
    public final static String ADMIN_LOGIN = "adminLogin";
    public final static String ADMIN_LOGOUT = "adminLogout";
    public final static String DELETE = "delete";
    public final static String RIDE = "ride";
    public final static String CALCULATE_COST = "calculateCost";
    public final static String REGISTER_DRIVER = "registerDriver";
    public final static String ADD_CAR = "addTaxiType";
    public final static String RIDES_STATISTICS = "ridesStatistics";
    public final static String ADD_TAXI_TYPE = "addTaxiType";
    public final static String TAXI_TYPES = "taxitypes";
    public final static String UPDATE_TAXI_TYPE = "updateTaxiType";
    public final static String CLIENT_TYPES = "clienttypes";
    public final static String ADD_CLIENT_TYPE = "addClientType";
    public final static String UPDATE_CLIENT_TYPE = "updateClientType";
    public final static String RIDE_ORDER = "rideOrder";
    private final Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put(LOGIN, new edu.goncharova.command.LoginCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(LOGOUT, new edu.goncharova.command.LogoutCommand());
        commandMap.put(USERS, new UsersCommand());
        commandMap.put(ADMIN_LOGIN, new LoginCommand());
        commandMap.put(DELETE, new DeleteCommand());
        commandMap.put(CALCULATE_COST, new CostCalculationCommand());
        commandMap.put(RIDE, new RideCommand());
        commandMap.put(REGISTER_DRIVER, new RegisterDriverCommand());
        commandMap.put(ADD_CAR, new AddCarCommand());
        commandMap.put(RIDES_STATISTICS, new RideStatisticsCommand());
        commandMap.put(ADD_TAXI_TYPE, new AddTaxiTypeCommand());
        commandMap.put(TAXI_TYPES, new TaxiTypesCommand());
        commandMap.put(UPDATE_TAXI_TYPE, new UpdateTaxiTypeCommand());
        commandMap.put(CLIENT_TYPES, new ClientTypesCommand());
        commandMap.put(ADD_CLIENT_TYPE, new AddClientTypeCommand());
        commandMap.put(UPDATE_CLIENT_TYPE, new UpdateClientTypeCommand());
        commandMap.put(RIDE_ORDER, new RideOrderCommand());
        commandMap.put(ADMIN_LOGOUT, new LogoutCommand());
    }

    public static CommandFactory getInstance() {
        return factory;
    }

    public Command getCommand(String command) {
        return commandMap.get(command);
    }
}
