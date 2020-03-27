/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 03-19-2020
 *  Description: SLIDER PUZZLE - board(all methods should be N^2 or better)
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int[] tiles;
    private final int dim;
    private int hamdist;
    private int mandist;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("cannot be null.");
        this.dim = tiles.length;
        int[] temp = new int[dim * dim];

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                temp[i * dim + j] = tiles[i][j];
            }
        }
        this.tiles = Arrays.copyOf(temp, dim * dim);
    }

    private Board(int[] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("cannot be null.");
        this.dim = (int) Math.sqrt(tiles.length);
        this.tiles = Arrays.copyOf(tiles, dim * dim);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.dimension() + "\n");
        for (int i = 0; i < tiles.length; i++) {
            s.append(String.format("%2d ", tiles[i]));
            if (i % this.dimension() == this.dimension() - 1)
                s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return (int) Math.sqrt(tiles.length);
    }

    // number of tiles out of place
    public int hamming() {
        hamdist = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != i + 1 && tiles[i] != 0)
                hamdist++;
        }
        return hamdist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        mandist = 0;
        int dim = this.dimension();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != i + 1 && tiles[i] != 0)
                mandist += Math.abs((tiles[i] - 1) / dim - i / dim) + Math
                        .abs((tiles[i] - 1) % dim - i % dim);
        }
        return mandist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != i + 1 && tiles[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // does this board equal y? 1.same size 2.coressponding tiles are at same places
    public boolean equals(Object y) {
        // Object y is not Board
        if (!(y instanceof Board))
            return false;
        // Object y has diff. dimension as this
        if (this.dimension() != ((Board) y).dimension())
            return false;
        // when y is null, always return false
        if (y == null)
            return false;
        // when ref to the same object
        if (y == this)
            return true;
        // Object y has diff. corresponding tiles as this
        for (int i = 0; i < tiles.length; i++) {
            if (this.tiles[i] != ((Board) y).tiles[i])
                return false;
        }
        // passed all condition above return true
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int idx = 0;
        int dim = this.dimension();
        Stack<Board> board_buckle = new Stack<>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) idx = i; // the index of zero
        }
        // corner cases:
        if (idx / dim == 0) {
            // upper left: +1, +dim
            if (idx % dim == 0) {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx + 1];
                clone1.tiles[idx + 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx + dim];
                clone2.tiles[idx + dim] = 0;
                board_buckle.push(clone2);
            }
            // upper right:  -1, +dim
            else if (idx % dim == dim - 1) {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx - 1];
                clone1.tiles[idx - 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx + dim];
                clone2.tiles[idx + dim] = 0;
                board_buckle.push(clone2);
            }
            // not metioned above: -1, +1, +dim
            else {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx + 1];
                clone1.tiles[idx + 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx + dim];
                clone2.tiles[idx + dim] = 0;
                board_buckle.push(clone2);

                Board clone3 = new Board(tiles);
                clone3.tiles[idx] = clone3.tiles[idx - 1];
                clone3.tiles[idx - 1] = 0;
                board_buckle.push(clone3);
            }

        }

        else if (idx / dim == dim - 1) {
            // lower left: +1, -dim
            if (idx % dim == 0) {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx + 1];
                clone1.tiles[idx + 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx - dim];
                clone2.tiles[idx - dim] = 0;
                board_buckle.push(clone2);
            }
            // lower right: -1, -dim
            else if (idx % dim == dim - 1) {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx - 1];
                clone1.tiles[idx - 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx - dim];
                clone2.tiles[idx - dim] = 0;
                board_buckle.push(clone2);
            }
            //not metioned above: -1, +1, -dim
            else {
                Board clone1 = new Board(tiles);
                clone1.tiles[idx] = clone1.tiles[idx + 1];
                clone1.tiles[idx + 1] = 0;
                board_buckle.push(clone1);

                Board clone2 = new Board(tiles);
                clone2.tiles[idx] = clone2.tiles[idx - dim];
                clone2.tiles[idx - dim] = 0;
                board_buckle.push(clone2);

                Board clone3 = new Board(tiles);
                clone3.tiles[idx] = clone3.tiles[idx - 1];
                clone3.tiles[idx - 1] = 0;
                board_buckle.push(clone3);
            }
        }
        // on left boundary: +1, -dim, +dim
        else if (idx % dim == 0 && (idx / dim != 0 || idx / dim != dim - 1)) {
            Board clone1 = new Board(tiles);
            clone1.tiles[idx] = clone1.tiles[idx + 1];
            clone1.tiles[idx + 1] = 0;
            board_buckle.push(clone1);

            Board clone2 = new Board(tiles);
            clone2.tiles[idx] = clone2.tiles[idx - dim];
            clone2.tiles[idx - dim] = 0;
            board_buckle.push(clone2);

            Board clone3 = new Board(tiles);
            clone3.tiles[idx] = clone3.tiles[idx + dim];
            clone3.tiles[idx + dim] = 0;
            board_buckle.push(clone3);
        }
        // on right boundary: -1, -dim, +dim
        else if (idx % dim == dim - 1 && (idx / dim != 0 || idx / dim != dim - 1)) {
            Board clone1 = new Board(tiles);
            clone1.tiles[idx] = clone1.tiles[idx - 1];
            clone1.tiles[idx - 1] = 0;
            board_buckle.push(clone1);

            Board clone2 = new Board(tiles);
            clone2.tiles[idx] = clone2.tiles[idx - dim];
            clone2.tiles[idx - dim] = 0;
            board_buckle.push(clone2);

            Board clone3 = new Board(tiles);
            clone3.tiles[idx] = clone3.tiles[idx + dim];
            clone3.tiles[idx + dim] = 0;
            board_buckle.push(clone3);
        }
        // -1, +1, -dim, +dim
        else {
            Board clone1 = new Board(tiles);
            clone1.tiles[idx] = clone1.tiles[idx + 1];
            clone1.tiles[idx + 1] = 0;
            board_buckle.push(clone1);

            Board clone2 = new Board(tiles);
            clone2.tiles[idx] = clone2.tiles[idx - dim];
            clone2.tiles[idx - dim] = 0;
            board_buckle.push(clone2);

            Board clone3 = new Board(tiles);
            clone3.tiles[idx] = clone3.tiles[idx + dim];
            clone3.tiles[idx + dim] = 0;
            board_buckle.push(clone3);

            Board clone4 = new Board(tiles);
            clone4.tiles[idx] = clone4.tiles[idx - 1];
            clone4.tiles[idx - 1] = 0;
            board_buckle.push(clone4);

        }

        return board_buckle;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(Arrays.copyOf(tiles, tiles.length));
        int temp;
        for (int i = 0; i < twin.tiles.length; i++) {
            if (twin.tiles[i] != 0) {
                if (twin.tiles[i + 1] != 0) {
                    temp = twin.tiles[i + 1];
                    twin.tiles[i + 1] = twin.tiles[i];
                    twin.tiles[i] = temp;
                    return twin;
                }
                else {
                    temp = twin.tiles[i + twin.dimension()];
                    twin.tiles[i + twin.dimension()] = twin.tiles[i];
                    twin.tiles[i] = temp;
                    return twin;
                }
            }

        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[] tiles = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            tiles[i] = in.readInt();
        }
        Board test = new Board(tiles);
        StdOut.println("M_dis: " + test.manhattan());
        StdOut.println("H_dis: " + test.hamming());
        StdOut.println(test);
        StdOut.println(test.twin());
        for (Board b : test.twin().neighbors()) {
            StdOut.println(b);
        }
    }

}
