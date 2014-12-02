package controller;

import java.util.ArrayList;

import controller.Crowd.Boid;
import model.KDTree;

public class Test
{

	public static void main(String[] args) 
	{
		Crowd crowd = new Crowd();
		
		KDTree tree = new KDTree();
		Boid boid1 = crowd.new Boid(1, 50, 0, 0);
		Boid boid2 = crowd.new Boid(5, 10, 0, 0);
		Boid boid3 = crowd.new Boid(7, 5, 0, 0);
		Boid boid4 = crowd.new Boid(10, 0, 0, 0);
		Boid boid5 = crowd.new Boid(15, -5, 0, 0);
		Boid boid6 = crowd.new Boid(20, -10, 0, 0);
		Boid boid7 = crowd.new Boid(25, -15, 0, 0);
		Boid boid8 = crowd.new Boid(30, -20, 0, 0);
		
		ArrayList<Boid> boids = new ArrayList<Boid>();
		boids.add(boid1);
		boids.add(boid2);
		boids.add(boid3);
		boids.add(boid4);
		boids.add(boid5);
		boids.add(boid6);
		boids.add(boid7);
		boids.add(boid8);
		
		tree.buildTree(boids);
		
		ArrayList<Boid> neighbours = tree.findNeighbours(400, boid3);
		System.out.println(neighbours.size());
	}
}
