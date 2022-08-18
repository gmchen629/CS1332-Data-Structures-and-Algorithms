import java.util.*;
import java.util.ArrayList;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Guanming Chen
 * @version 1.0
 * @userid gchen353
 * @GTID 903661790
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Error, any input is null, or if start " + "doesn't exist in the graph");
        }
        Queue<Vertex<T>> q = new LinkedList<>();
        List<Vertex<T>> isVisited = new ArrayList<>();
        q.offer(start);
        isVisited.add(start);
        while (!q.isEmpty()) {
            Vertex<T> curNode = q.poll();
            List<VertexDistance<T>> distanceList = graph.getAdjList().get(curNode);
            for (VertexDistance<T> distance : distanceList) {
                Vertex<T> curVertex = distance.getVertex();
                if (isVisited.contains(curVertex)) {
                    continue;
                } else {
                    q.offer(curVertex);
                    isVisited.add(curVertex);
                }
            }
        }
        return isVisited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Error, any input is null, or if start " + "doesn't exist in the graph");
        }
        List<Vertex<T>> res = new ArrayList<>();
        Set<Vertex<T>> isVisited = new HashSet<>();
        dfs(start, isVisited, res, graph);
        return res;
    }

    /**
     * helper method for recursive DFS
     * @param <T> the generic typing of the data
     * @param curNode current node
     * @param isVisited list that contains all visited nodes
     * @param res  result list
     * @param graph graph to search through
     */

    private static <T> void dfs(Vertex<T> curNode, Set<Vertex<T>> isVisited, List<Vertex<T>> res, Graph<T> graph) {
        isVisited.add(curNode);
        res.add(curNode);
        for (VertexDistance<T> neighbor: graph.getAdjList().get(curNode)) {
            Vertex<T> neighborVertex = neighbor.getVertex();
            if (isVisited.contains(neighborVertex)) {
                continue;
            } else {
                dfs(neighborVertex, isVisited, res, graph);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Error, any input is null, or if start " + "doesn't exist in the graph");
        }
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        List<Vertex<T>> isVisited = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> distanceMap = graph.getAdjList();
        Map<Vertex<T>, Integer> res = new HashMap<>();
        pq.offer(new VertexDistance<>(start, 0));
        for (Vertex<T> vertex : distanceMap.keySet()) {
            res.put(vertex, Integer.MAX_VALUE);
        }
        res.put(start, 0);
        while (!(pq.isEmpty()) && isVisited.size() < distanceMap.size()) {
            VertexDistance<T> curr = pq.poll();
            if (!isVisited.contains(curr.getVertex())) {
                isVisited.add(curr.getVertex());
                for (VertexDistance<T> distance : distanceMap.get(curr.getVertex())) {
                    int newDistance = curr.getDistance() + distance.getDistance();
                    if (!isVisited.contains(distance.getVertex()) && newDistance < res.get(distance.getVertex())) {
                        pq.offer(new VertexDistance<>(distance.getVertex(), newDistance));
                        res.put(distance.getVertex(), newDistance);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Error, any input is null");
        }
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        for (Edge<T> edge : graph.getEdges()) {
            pq.offer(edge);
        }
        while (!pq.isEmpty() && mst.size() < graph.getEdges().size() - 1) {
            Edge<T> curEdge = pq.poll();
            Vertex<T> curVertexU = curEdge.getU();
            Vertex<T> curVertexV = curEdge.getV();
            Vertex<T> rootU = disjointSet.find(curVertexU);
            Vertex<T> rootV = disjointSet.find(curVertexV);
            if (rootV != rootU) {
                disjointSet.union(rootU, rootV);
                mst.add(curEdge);
                Edge<T> newEdge = new Edge<>(curVertexV, curVertexU, curEdge.getWeight());
                mst.add(newEdge);
            }
        }
        return (mst.size() < 2 * (graph.getVertices().size() - 1)) ? null : mst;
    }
}
