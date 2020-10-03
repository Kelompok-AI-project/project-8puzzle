package com.project.Project_8_Puzzle;

import java.util.Comparator;

public class astar implements Cloneable{
    String now;
    astar parent;
    int cost,depth;

    public astar(String now, astar parent, int cost, int depth) {
        this.now = now;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
    }

    public int getDepth(){
        return this.depth+1;
    }

}


