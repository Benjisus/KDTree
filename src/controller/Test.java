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
		
		Boid[] xList = {boid1, boid2, boid3, boid4};
		Boid[] yList = {boid4, boid3, boid2, boid1};
		
		
		tree.buildTree(xList, yList);
	}

}
