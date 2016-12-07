package ru.rudn.bubble;

import io.vertx.core.Vertx;
import sun.security.provider.certpath.Vertex;

import java.util.Scanner;

/**
 * Created by Rexhaif on 07.12.2016.
 */
public class Launcher {


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpVerticle());

    }
}
