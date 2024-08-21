public class Friday {
    private final String header = """
            ____________________________________________________________
             Hello! I'm Friday
             What can I do for you?
            ____________________________________________________________
            """;

    private final String ending = """
             Bye. Hope to see you again soon!
            ____________________________________________________________
            """;

    

    public static void main(String[] args) {
        Friday bot = new Friday();
        System.out.println(bot.header + bot.ending);
    }
}
