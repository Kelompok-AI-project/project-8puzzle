package com.project.Project_8_Puzzle;

import java.util.Stack;

public class bfs {
    Stack<String> parent;
    bfs child;
    String state_parent;

    public bfs(Stack<String> parent, bfs child, String state_parent) {
        this.parent = parent;
        this.child = child;
        this.state_parent = state_parent;
    }

    public Stack<String> getParent() {
        return parent;
    }

    public void setParent(Stack<String> parent) {
        this.parent = parent;
    }

    public bfs getChild() {
        return child;
    }

    public void setChild(bfs child) {
        this.child = child;
    }

    public String getState_parent() {
        return state_parent;
    }

    public void setState_parent(String state_parent) {
        this.state_parent = state_parent;
    }
}
