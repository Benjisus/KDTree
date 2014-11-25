package controller;

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
		
		Boid[] xList = {boid1, boid2, boid3, boid4, boid5, boid6, boid7, boid8};
		Boid[] yList = {boid8, boid7, boid6, boid5, boid4, boid3, boid2, boid1};
		
		
		tree.buildTree(xList, yList);
		tree.print();
	}

}
