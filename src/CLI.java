import java.util.Scanner;

public class CLI {
    public static final char COMMAND_HELP = 'h';
    public static final char COMMAND_NEW_GAME = 'n';
    public static final char COMMAND_CANCEL = 'c';
    public static final char COMMAND_WIN = 'x'; // easter egg :)))

    public static final String CLI_PROMPT = "> ";

    private void prompt() {
        System.out.print(CLI_PROMPT);
    }

    private void help() {
        System.out.println("Commands:\n" +
                "h - help\n" +
                "n - new game\n" +
                "c - interrupt game or exit from game\n");
        System.out.println("To choose cell you can enter coordinates\n" +
                "in the format 'a1', where 'a' - letter of the row\n" +
                "and '1' - digit of the column. For example, if you\n" +
                "want to choose cell in the 1st row and 2nd column\n" +
                "you must to enter 'a2'.\n" +
                "Just play. Have fun. Enjoy the game. :)\n");
    }

    private void incorrectCommandWarning() {
        System.out.println("Incorrect command.\n" +
                "Enter 'h' to help.");
    }

    private void newGame() {
        System.out.println("Game is started.\n" +
                "First move belongs to player X");
        Game game = new Game();

        Scanner scanner = new Scanner(System.in);
        String move;
        while (game.getGameStatus() == game.STATUS_UNDEFINED) {
            if (game.getCurrentPlayer()==game.PLAYER_0) {
                System.out.print("0");
            } else {
                System.out.print("X");
            }
            prompt();
            move = scanner.nextLine();
            game.nextMove(move);
            game.writeField();
        }

        switch (game.getGameStatus()) {
            case Game.STATUS_WIN_0:
                System.out.println("PLAYER 0 WIN!");
                break;

            case Game.STATUS_WIN_X:
                System.out.println("PLAYER X WIN!");
                break;

            case Game.STATUS_DRAW:
                System.out.println("DRAW");
                break;

            case Game.STATUS_INTERRUPTED:
                System.out.println("GAME INTERRUPTED");
                break;
        }
    }

    public void run() {
        System.out.println("This is tic-tac-toe game!");
        help();

        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            prompt();
            command = scanner.nextLine().toLowerCase().trim();
            if (command.length() > 0) {
                switch (command.charAt(0)) {
                    case COMMAND_HELP:
                        help();
                        break;

                    case COMMAND_NEW_GAME:
                        newGame();
                        break;

                    case COMMAND_CANCEL:
                        return;

                    default:
                        incorrectCommandWarning();
                }
            }
        }
    }

}
