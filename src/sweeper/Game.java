package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;

    private GameState state;

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    public int getTotalBombs() {
        return bomb.getTotalBombs();
    }

    public int getTotalFlaged() {
        return flag.getTotalFlaged();
    }

    public void pressLeftButton(Coord coord) {
        if (isGameOver()) return;
        openBox(coord);
        checkWinner();
    }

    public void pressRightButton(Coord coord) {
        if (isGameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean isGameOver() {
        return state != GameState.PLAYED;
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getTotalClosed() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
                flag.setFlagedToLastClosedBoxes();
            }
        }
    }

    public GameState getState() {
        return state;
    }

    private void openBox(Coord coord) {
        switch(flag.get(coord)) {
            case OPENED:
                break;
            case FLAGED:
                break;
            case CLOSED:
                switch(bomb.get(coord)) {
                    case ZERO:
                        openBoxesAroundZero(coord);
                        break;
                    case BOMB:
                        openBombs(coord);
                        break;
                    default:
                        flag.setOpenedToBox(coord);
                        break;
                }
        }
    }

    private void openBombs(Coord bombedCoord) {
        flag.setBombedToBox(bombedCoord);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setClosedToOpenedBox(coord);
            } else {
                flag.setNobombToFlagedBox(coord);
            }
        }
        state = GameState.BOMBED;
    }

    private void openBoxesAroundZero(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }
}
