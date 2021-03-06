import java.util.Arrays;

/**
 * Created by Andreas on 2014-11-27.
 */
public class Graph {

    private Node[] nodes;
    public int[][] distances;
    public short[][] neighbors;
    private int numNodes = 0;
//    public int numNeighbors = 100;
    public int numNeighbors = 100;

    private class Node {
        public double x;
        public double y;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


    public Graph(int n) {
        this.numNodes = n;
        this.distances = new int[n][n];
        //this.neighborhood = new short[n][n];
        this.nodes = new Node[n];

        if (n < this.numNeighbors)
            this.numNeighbors = n;
        //this.numNeighbors = n;
    }

    public void addNode(int i, double x, double y) {
        nodes[i] = new Node(x,y);

        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] != null) {
                distances[i][j] =  (int) Math.round(Math.sqrt((nodes[i].x-nodes[j].x) * (nodes[i].x-nodes[j].x) + (nodes[i].y-nodes[j].y) * (nodes[i].y-nodes[j].y)));
                distances[j][i] = distances[i][j];
            }
        }
    }

    public void getNeighbors() {
            if (numNeighbors >= numNodes)
                numNeighbors = numNodes-1;
            neighbors =  new short[numNodes][numNeighbors];


            double dist;
            for (short i = 0; i < numNodes; i++) {
                Arrays.fill(neighbors[i], (short)-1);

                innerFor:
                for (short n = 0; n < numNodes; n++) {
                    if (i != n) {
                        dist = distances[i][n];

                        if (neighbors[i][numNeighbors -1] != -1 && dist > distances[i][neighbors[i][numNeighbors -1]])
                            continue;

                        int min=0, max, mid;
                        max = numNeighbors;

                        while(max-min <= 4) {
                            mid = (min + max)/2;
                            if (dist > distances[i][neighbors[i][mid]])
                                min = mid +1;
                            else
                                max = mid -1;
                        }

                        for (int j = min; j < max; j++) {

                            if (neighbors[i][j] == -1 || (distances[i][neighbors[i][j]] > dist)) {

                                int k = numNeighbors -1;
                                while (k > j) {
                                    neighbors[i][k] = neighbors[i][k-1];
                                    k--;
                                }
                                neighbors[i][j] = n;
                                continue innerFor;

                            }
                        }
                    }
                }
            }
    }



    public int getNodes() {
        return numNodes;
    }

    public double getDistance(Solution solution) {
        double distance = 0;
        short node = 0;
        for (int i = 0; i < solution.path.length - 1; i++) {
            distance += distances[node][solution.path[node]];
            node = solution.path[node];
        }
        distance += distances[node][0];
        return distance;
    }
}
