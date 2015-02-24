import java.util.Arrays;

public class Game {
    public static final int FIELD_SIZE = 3;

    public static final byte CELL_EMPTY = -1;
    public static final byte CELL_0 = 0;
    public static final byte CELL_X = 1;

    public static final byte PLAYER_0 = 0;
    public static final byte PLAYER_X = 1;

    public static final byte STATUS_UNDEFINED = -1;
    public static final byte STATUS_WIN_0 = 0;
    public static final byte STATUS_WIN_X = 1;
    public static final byte STATUS_DRAW = 2;
    public static final byte STATUS_INTERRUPTED = 3;

    private byte[][] field;

    private byte currentPlayer;
    private byte gameStatus;

    public byte getGameStatus() {
        return gameStatus;
    }

    public byte getCurrentPlayer() {
        return currentPlayer;
    }

    Game() {
        field = new byte[FIELD_SIZE][FIELD_SIZE];
        for (byte[] aField : field) {
            Arrays.fill(aField, CELL_EMPTY);
        }
        currentPlayer = PLAYER_X;
        gameStatus = STATUS_UNDEFINED;
    }

    public void nextMove(String move) {
        //TODO: validate step string
        int row = move.charAt(0) - 'a';
        int col = move.charAt(1) - '1';

        setFieldCell(row, col);
        checkStatus(row, col);
        changePlayer();
    }

    private void changePlayer() {
        if (currentPlayer == PLAYER_0) {
            currentPlayer = PLAYER_X;
        } else {
            currentPlayer = PLAYER_0;
        }
    }

    private void setFieldCell(int row, int col) {
        field[row][col] = currentPlayer;
    }

    private boolean checkSequence(int row, int col, int incRow, int incCol) {
        boolean isWin = true;

        if (field[row][col] == CELL_EMPTY) {
            return false;
        }

        for (int i=row, j=col; i<FIELD_SIZE && j<FIELD_SIZE && isWin; i+=incRow, j+=incCol) {
            isWin = field[i][j]==field[row][col];
        }

        return isWin;
    }

    private boolean checkDraw() {
        boolean isDraw = true;

        for (int i=0; i<FIELD_SIZE && isDraw; i++) {
            for (int j=0; j<FIELD_SIZE; j++) {
                isDraw = field[i][j]!=CELL_EMPTY;
            }
        }

        return isDraw;
    }

    private void checkStatus(int row, int col) {
        if (checkSequence(row, 0, 0, 1) || // horizontal
                checkSequence(0, col, 1, 0) || // vertical
                checkSequence(0, 0, 1, 1) || // diagonal 1
                checkSequence(0, FIELD_SIZE-1, 1, -1)) // diagonal 2
        {
            gameStatus = (currentPlayer == PLAYER_0) ? STATUS_WIN_0 : STATUS_WIN_X;
        } else if (checkDraw()) {
            gameStatus = STATUS_DRAW;
        }
    }

    public void writeField() {
        String emptyTemplate = "t ";

        System.out.print(emptyTemplate.replace("t", " "));
        for (int i=0; i<FIELD_SIZE; i++) {
            System.out.print(emptyTemplate.replace("t", "" + (char)('1'+i)));
        }
        System.out.println();

        for (int i=0; i<FIELD_SIZE; i++) {
            System.out.print(emptyTemplate.replace("t", "" + (char)('a' + i)));
            for (int j=0; j<FIELD_SIZE; j++) {
                switch (field[i][j]) {
                    case CELL_EMPTY:
                        System.out.print(emptyTemplate.replace("t", " "));
                        break;

                    case CELL_0:
                        System.out.print(emptyTemplate.replace("t", "O"));
                        break;

                    case CELL_X:
                        System.out.print(emptyTemplate.replace("t", "X"));
                        break;
                }
            }
            System.out.println();
        }
    }
}
