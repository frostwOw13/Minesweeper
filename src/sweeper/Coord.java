package sweeper;

public class Coord {
    public int x;
    public int y;

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord)) {
            return super.equals(obj);
        }

        Coord coord = (Coord) obj;
        return coord.x == x && coord.y == y;
    }
}
