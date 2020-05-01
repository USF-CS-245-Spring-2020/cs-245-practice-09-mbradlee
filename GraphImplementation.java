/* @file GraphImplementation
 *
 * @author Marcus Bradlee
 */


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class GraphImplementation implements Graph {

    private boolean adjacencyMatrix[][];

    public GraphImplementation(int vertices){
        adjacencyMatrix = new boolean[vertices][vertices];
    }

    /* addEdge is a function which connects two vertices with a directed edge,
     * as long as the vertices already exist in the graph. Given two ID's of
     * vertices, v1 and v2, this function will set v1 as the source and v2 as
     * the destination.
     * @param int v1, src vertex ID, int v2, dest vertex ID
     * @return void
     */

    public void addEdge(int v1, int v2) throws Exception{
        if(v1 > adjacencyMatrix.length || v2 > adjacencyMatrix.length || v1 < 0 || v2 < 0){
            throw new IndexOutOfBoundsException();
        }
        if(!adjacencyMatrix[v1][v2])
            adjacencyMatrix[v1][v2] = true;
        else
            System.out.println("edge already exists");
    }

    /* topologicalSort is a function which provides an ordering of vertices
     * in such a way that the preceding vertices are always sorted before their
     * directed counterparts. There are certain scenarios, such as a cyclical graph
     * where this is impossible, in which case this function will throw an exception.
     * @param
     * @return List<Integer> schedule, the toplogically sorted (or partial) list of vertices
     */

    public List<Integer> topologicalSort(){
        List<Integer> schedule = new ArrayList<>();

        int[] incidents = new int[adjacencyMatrix.length];
        for(int v = 0; v < adjacencyMatrix.length; v++){
            for(int w = 0; w < adjacencyMatrix.length; w++){
                if(adjacencyMatrix[w][v]){
                    incidents[v] += 1;
                }
            }
        }

        for(int i = 0; i < adjacencyMatrix.length; i++){
            int v = noIncidents(incidents);
            if(v == -1){
                System.out.println("Could not complete sort.");
                throw new NoSuchElementException();
            }
            schedule.add(v);
            incidents[v] = -1;
            for(int j = 0; j < adjacencyMatrix.length; j++){
                if(adjacencyMatrix[v][j]){
                    incidents[j] -= 1;
                }
            }
        }

        return schedule;
    }

    /* noIncidents is a helper function for topological sort which recieves
     * the integer array of incidents, meaning each index is the vertex ID and
     * the value is the corresponding incoming edges. This function simply
     * iterates and returns the index of the first 0 it finds, otherwise it
     * returns -1
     * @param int[] incidents, the array of incoming edges
     * @return i, index upon discovery of 0 incoming edges, -1, upon failure to find a 0
     */

    private int noIncidents(int[] incidents){
        for(int i = 0; i < incidents.length; i++){
            if(incidents[i] == 0){
                return i;
            }
        }
        return -1;
    }

    /* neighbors is a functions which recieves an integer vertex and returns
     * a list of all vertices that are in the given vertex's adjacent path
     * @param int vertex, the source vertex
     * @return List<Integer> nbrs, the list of neighbors
     */

    public List<Integer> neighbors(int vertex) throws Exception{
        List<Integer> nbrs = new ArrayList<>();
        if(vertex < 0 || vertex > adjacencyMatrix.length){
            throw new IndexOutOfBoundsException();
        }

        for(int i = 0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[vertex][i]){
                nbrs.add(i);
            }
        }

        return nbrs;
    }

}
