package com.project.Project_8_Puzzle;

import java.util.Stack;

public class bfs {
    public String now;
    public bfs parent;


    public bfs(String now) {
        this.now = now;
        parent = null;
    }


    public bfs(String now, bfs parent) {
        this.now = now;
        this.parent = parent;
    }
}
