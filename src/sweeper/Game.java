package sweeper;

public class Game {
    Matrix bombMap;
    public Game(int cols, int rows) {
        Ranges.setSize(new Coord(cols, rows));
    }

    public void start() {
        bombMap = new Matrix(Box.ZERO);
        bombMap.set(new Coord(0, 0), Box.BOMB);
    }

    public Box getBox(Coord coord) {
        return bombMap.get(coord);
    }
}
