import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLI {
    public static final char COMMAND_HELP = 'h';
    public static final char COMMAND_NEW_GAME = 'n';
    public static final char COMMAND_CANCEL = 'c';
    public static final char COMMAND_WIN = 'x'; // easter egg :)))

    public static final String CLI_PROMPT = "> ";

    private static final String MOVE_PATTERN_STRING = "[a-c][1-3]";
    private static final String GAME_COMMAND_PATTERN_STRING = "[hcx]";
    private static final String CLI_COMMAND_PATTERN_STRING = "[hnc]";

    private void prompt() {
        System.out.print(CLI_PROMPT);
    }

    private void cliHelp() {
        System.out.println("Commands:\n" +
                "h - help\n" +
                "n - new game\n" +
                "c - interrupt game or exit from game");
    }

    private void gameHelp() {
        System.out.println("To choose cell you can enter coordinates\n" +
                "in the format 'a1', where 'a' - letter of the row\n" +
                "and '1' - digit of the column. For example, if you\n" +
                "want to choose cell in the 1st row and 2nd column\n" +
                "you must to enter 'a2'.\n");
    }

    private void incorrectCommandWarning() {
        System.out.println("Incorrect command.\n" +
                "Enter 'h' to help.");
    }

    private void incorrectMoveWarning() {
        System.out.println("Incorrect move.\n" +
                "Enter 'h' to help.");
    }

    private void newGame() {
        System.out.print("\n" +
                "Game is started!\n" +
                "You can use 'h' and 'c' command and make move.\n" +
                "First move belongs to player X\n");
        Game game = new Game();

        Scanner scanner = new Scanner(System.in);
        String move;

        Pattern commandPattern = Pattern.compile(GAME_COMMAND_PATTERN_STRING);
        Pattern movePattern = Pattern.compile(MOVE_PATTERN_STRING);
        Matcher matcher;

        while (game.getGameStatus() == Game.STATUS_UNDEFINED) {
            if (game.getCurrentPlayer()==Game.PLAYER_0) {
                System.out.print("0");
            } else {
                System.out.print("X");
            }
            prompt();

            // validate input string
            move = scanner.nextLine().toLowerCase().trim();
            matcher = commandPattern.matcher(move);
            if (matcher.matches()) { // if input is command
                if (move.length()==1) {
                    switch (move.charAt(0)) {
                        case COMMAND_HELP:
                            gameHelp();
                            break;

                        case COMMAND_CANCEL:
                            game.interruptGame();
                            break;

                        case COMMAND_WIN:
                            game.pressXToWin();
                            break;

                        default:
                            incorrectCommandWarning();
                    }
                }
            } else {
                matcher = movePattern.matcher(move);
                if (matcher.matches()) { // if input is players move
                    game.nextMove(move);
                    game.writeField();
                } else {
                    incorrectMoveWarning();
                }
            }
        }

        System.out.println("End of game.");
        game.writeResult();
        System.out.println();
    }

    public void run() {
        System.out.println("This is tic-tac-toe game!");
        cliHelp();
        gameHelp();
        System.out.println("Just play. Have fun. Enjoy the game. :)\n");

        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            prompt();
            command = scanner.nextLine().toLowerCase().trim();
            if (command.length() > 0) {
                Pattern pattern = Pattern.compile(CLI_COMMAND_PATTERN_STRING);
                Matcher matcher = pattern.matcher(command);
                if (matcher.matches()) {
                    switch (command.charAt(0)) {
                        case COMMAND_HELP:
                            cliHelp();
                            break;

                        case COMMAND_NEW_GAME:
                            newGame();
                            break;

                        case COMMAND_CANCEL:
                            return;
                    }
                } else {
                    incorrectCommandWarning();
                }
            }
        }
    }

}
