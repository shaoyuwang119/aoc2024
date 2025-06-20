public record Coords (int r, int c) {

    public Coords() {
        this(0, 0);
    }

    public int[] asArray() {
        return new int[] {r, c};
    }

    public Coords add(int r, int c) {
        return new Coords(this.r + r, this.c + c);
    }

    public Coords add(Coords pos) {
        return new Coords(this.r+pos.r(), this.c+pos.c());
    }

    @Override
    public String toString() {
        return "(" + r + ", " + c + ")";
    }
}
