package sweeper;

public class Flag {
    private Matrix flagMap;
    private int totalFlaged;
    private int totalClosed;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        totalFlaged = 0;
        totalClosed = Ranges.getSquare();
    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        totalClosed--;
    }

    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
        totalFlaged++;
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
        totalFlaged--;
    }

    void toggleFlagedToBox(Coord coord) {
        switch(flagMap.get(coord)) {
            case FLAGED:
                setClosedToBox(coord);
                break;
            case CLOSED:
                setFlagedToBox(coord);
                break;
        }
    }

    public int getTotalFlaged() {
        return totalFlaged;
    }

    public int getTotalClosed() {
        return totalClosed;
    }

    public void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords()) {
            if (get(coord) == Box.CLOSED) {
                setFlagedToBox(coord);
            }
        }
    }
}
