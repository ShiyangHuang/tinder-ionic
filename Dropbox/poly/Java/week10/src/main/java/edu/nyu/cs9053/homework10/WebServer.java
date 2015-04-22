package edu.nyu.cs9053.homework10;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:37 AM
 */
public class WebServer {

    private static final String USAGE =
            "Usage: WebServer type factor\n" +
            "  where 'type' is either 'thread' or 'executor'\n" +
            "  and   'factor' is the concurrency factor (i.e., number of threads to use when processing)";

    public static void main(String[] args) {
        if ((args == null) || (args.length != 2) ||
                (!"thread".equals(args[0]) && !"executor".equals(args[0]))) {
            System.out.printf("%s%n", USAGE);
            return;
        }
        int concurrencyFactor;
        try {
            concurrencyFactor = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.printf("%s%n", USAGE);
            return;
        }
        DoSDefense defense;
        if ("thread".equals(args[0])) {
            defense = DosDefenseFactory.createThreaded(concurrencyFactor);
        } else {
            defense = DosDefenseFactory.createExecutor(concurrencyFactor);
        }
        SiegeCommander siegeCommander = new SiegeCommander(defense, concurrencyFactor);
        siegeCommander.start();
    }

}
