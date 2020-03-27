/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 03-23-2020
 *  Description: SLIDER PUZZLE - A* solver
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves = 0;
    private boolean solvable;
    private final Stack<Board> sol_steps = new Stack<Board>(); // should be final to make immutable
    private node end;

    // subclass for node in priority queue
    private final class node implements Comparable<node> {
        private final int move;
        private final int man;
        private final Board board;
        private final node pred;

        // primitive comparison of priority
        public int compareTo(node that) {
            if (this.move + this.man > that.move + that.man) {
                return 1;
            }
            else if (this.move + this.man < that.move + that.man)
                return -1;
            else {
                // break tie by counting moves
                if (this.man > that.man)
                    return 1;
                else if (this.man < that.man)
                    return -1;
                else
                    return 0;
            }

        }

        public node(Board b, int move, node pred) {
            this.move = move;
            this.man = b.manhattan();
            this.board = b;
            this.pred = pred;
        }

    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Should not be null!");
        // create priority queue given comparator

        MinPQ<node> astar_pq = new MinPQ<node>(node::compareTo);
        MinPQ<node> astar_pqt = new MinPQ<node>(node::compareTo);
        node root = new node(initial, 0, null);
        node root_twin = new node(initial.twin(), 0, null);


        // either one of the branch over then the whole loop is finished
        while (root.man > 0 && root_twin.man > 0) {

            for (Board b : root_twin.board.neighbors()) {
                // first node
                if (root_twin.pred == null)
                    astar_pqt.insert(new node(b, root_twin.move + 1, root_twin));
                else {
                    // if the the node priority does not exists in previous root
                    // add it to queue
                    if (!b.equals(root_twin.pred.board))
                        astar_pqt.insert(new node(b, root_twin.move + 1, root_twin));
                }
            }
            //StdOut.println("Root:");
            //StdOut.println(root.board);
            //StdOut.println(root.board.manhattan());
            //StdOut.println("Neighbor:");

            for (Board b : root.board.neighbors()) {
                // if the the node priority does not exists in previous root
                // add it to queue

                //StdOut.println(b);
                //StdOut.println(b.manhattan());
                if (root.pred == null)
                    astar_pq.insert(new node(b, root.move + 1, root));
                else {
                    //StdOut.println("pred: " + root.pred.board);
                    //StdOut.println(b.equals(root.pred.board));
                    if (!b.equals(root.pred.board))
                        astar_pq.insert(new node(b, root.move + 1, root));
                }
            }
            // del min in queue and serve as the new root node:
            // both twin and org
            root_twin = astar_pqt.delMin();
            root = astar_pq.delMin();
            // if twin
            /*if (astar_pq.min().twin) {
                root_twin = astar_pq.delMin();
                while (astar_pq.min().twin)
                    astar_pq.delMin();
                root = astar_pq.delMin();
                //StdOut.println("twin_in");

            }
            // if origin
            else {
                root = astar_pq.delMin();
                while (!astar_pq.min().twin)
                    astar_pq.delMin();
                root_twin = astar_pq.delMin();
                // StdOut.println("org_in");
            }*/
        }


        // check solved by origin or twin
        if (root_twin.man == 0)
            solvable = false;
        else {
            solvable = true;
            moves = root.move;
            end = root;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (solvable)
            return moves;
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (solvable) {
            while (end.pred != null) {
                sol_steps.push(end.board);
                end = end.pred;
            }
            sol_steps.push(end.board);
            return sol_steps;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
