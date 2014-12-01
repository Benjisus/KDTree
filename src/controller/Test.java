package controller;

import java.util.ArrayList;

import model.KDTree;
import model.KDTree.Boid;

public class Test
{

	public static void main(String[] args) 
	{
		KDTree tree = new KDTree();
		Boid boid1 = tree.new Boid(1, 50);
		Boid boid2 = tree.new Boid(5, 10);
		Boid boid3 = tree.new Boid(7, 5);
		Boid boid4 = tree.new Boid(10, 0);
		Boid boid5 = tree.new Boid(15, -5);
		Boid boid6 = tree.new Boid(20, -10);
		Boid boid7 = tree.new Boid(25, -15);
		Boid boid8 = tree.new Boid(30, -20);
		
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
		
		ArrayList<KDTree.Boid> neighbours = tree.findNeighbours(20, boid3);
		System.out.println(neighbours.size());
	}
}
