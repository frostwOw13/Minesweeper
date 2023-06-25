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

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
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

    int getTotalFlaged() {
        return totalFlaged;
    }

    int getTotalClosed() {
        return totalClosed;
    }

    void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords()) {
            if (get(coord) == Box.CLOSED) {
                setFlagedToBox(coord);
            }
        }
    }

    void setOpenedToClosedBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }
    }

    void setNobombToFlagedBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }

    public int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.FLAGED) {
                count++;
            }
        }
        return count;
    }
}
