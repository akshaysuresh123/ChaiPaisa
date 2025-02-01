package com.example.chaipaisa.views;

import java.util.ArrayList;
import java.util.LinkedList;

public class test {

    public static void main(String[] args){

        Graph graph=new Graph(4);

        graph.addnode(new Node('A'));
        graph.addnode(new Node('B'));
        graph.addnode(new Node('C'));
        graph.addnode(new Node('D'));

        graph.addedge(0,1);
        graph.addedge(0,2);
        graph.addedge(1,3);
        graph.depthfirstsearch(3);

    }

}



class Graph {

    ArrayList<Node>nodes;
    int [][] matrix;




    Graph(int size) {
        matrix=new int[size][size];
        nodes=new ArrayList<>();

    }

    public void addnode(Node node){
        nodes.add(node);

    }
    public void addedge(int src,int dest){
        if(matrix[src][dest]==1){
            return;
        }else {
            matrix[src][dest]=1;
        }

    }
    public Boolean checkedge(){

        return true;
    }

    public void print(){

    }
    public void Dfhelper(int src,boolean[] visitd){

        try{

        if(visitd[src]){
            return;
        }else{
            visitd[src]=true;
            System.out.println(nodes.get(src).data+" : is visited");
        }
        }catch (NullPointerException e){
            System.out.print(e);
        }
        finally {
            visitd[src]=true;
            System.out.println(nodes.get(src)+" : is visited");
        }
        for(int i = 0;i<matrix[src].length;i++){
            if(matrix[src][i]==1){
                Dfhelper(i,visitd);
            }
        }
        return;



    }

    public void depthfirstsearch(int src){
        boolean[] visited = new boolean[matrix.length];

        Dfhelper(src,visited);

    }
}

class Node{
    char data;
    Node(char data){
        this.data=data;

    }

}
