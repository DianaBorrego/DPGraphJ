# DPGraphJ

#### DPGraphJ is written in Java

## Purpose

DPGraphJ is intended for teachers, students, scientists, or more generally anyone with minimal skills in Java programming who wish to systematically solve optimisation problems using dynamic programming.

## Requirements

Java $\geq$ 18

## Usage

The main goal of DPGraphJ is...

PONER ALGO DE MODULE-INFO?

## Project tree

AQUI PONER EL CONTENIDO DE CADA PROYECTO, O SOLO DEL PRINCIPAL Y DESPUES DETALLAR EL DE EJEMPLOS

## Getting started with an example

For solving a Dynamic Programming problem, you have to implement some specific code. Let's go through the steps with the example of the *equipment replacement*, which is stated as follows: 

The problem seeks to determine an optimal replacement policy for a single piece of equipment of age *i* over a time horizon of *n* units. We assume:

- The part can have, at most, a life of *m* years after which it can no longer be in use.
- The annual maintenance cost is *c(i)*.
- The purchase price for a unit is *t(i)*. Where *t(0)* is the price of (buying) a new unit and *t(m)* is the residual (selling) price at the end of its operating life.
- The initial age is *e_0*.
- At each time unit, the possible decisions are *M*, *C* (keep, change) consisting of keeping the part at the current instant if possible or changing it for a new one.

To solve this problem by dynamic programming using DPGraphJ we must first implement the record that models the type of the solution:

```java
public record SolStringDouble(String s, Double weight) {
	public static SolStringDouble of(String s, Double weight) {
		return new SolStringDouble(s, weight);
	}
}
```
Next we must implement the record for the vertex, which implements the HyperVertex interface, starting with the initial vertex, the of method for vertex creation and the attributes.

```java
public record EquipReplaceVertex(Integer i,Integer j,Integer k) 
		implements HyperVertex<EquipReplaceVertex,EquipReplaceEdge,Integer,SolStringDouble> {
	
	
	public static EquipReplaceVertex initial() {	
		return new EquipReplaceVertex(e0,0,N);
	}
	
	public static EquipReplaceVertex of(Integer i,Integer j,Integer k) {	
		return new EquipReplaceVertex(i,j,k);
	}

	public static int N; //number of periods
	public static int M; //maximum age
	public static int e0; //initial age
	public static List<Integer> operatingCost; //maintenance cost according to age
	public static List<Integer> tradeinCost; //cost to exchange for a new one according to age
	public static int priceNew; //price of new equipment
```

Then, the methods of the HyperVertex interface are implemented, although some of them do not require it because they have default implementations. 

```java
  //list of alternatives of the current problem
  public List<Integer> alternatives() {
		if (k>1)
			return List2.rangeList(1, M+1);
		else 
			return List.of();
	}
  //true in case the current problem is a base case
  public Boolean isBaseCase() {
		return  k == 1; 
	}
  //weight of the solution to the problem when this is a base case, being the weight the value for the objective function to be optimised
  public Double baseCaseSolutionWeight() {
		Double r = null;
		if (j==1) {
			r = (double) (tradeinCost.get(0)-tradeinCost.get(i)+operatingCost.get(0));
		} else if (j==i+1) {
			r = (double) operatingCost.get(i);
		}
		return r;
	}
  //solution to the problem when it is a base case
  public SolStringDouble baseCaseSolution() {
		Double weight = baseCaseSolutionWeight();
		String s=null;
		if (j==1) {
			s="1";
		} else if (j==i+1) {
			s=j.toString();
		}
		return SolStringDouble.of(s, weight);
	}
  //list of subproblems (i.e. vertices) that are neighbors of the current problem when considering alternative a
  public List<EquipReplaceVertex> neighbors(Integer a) {
		List<EquipReplaceVertex> r=null;
		if (j==0) {
			r = List.of(this.neighbor(i, a, k));
		} else {
			r = List.of(this.neighbor(i, a, k/2), this.neighbor(a, j, k-k/2));
		}
		return r;
	}
  
  public EquipReplaceVertex neighbor(Integer i, Integer j, Integer k){
		return new EquipReplaceVertex(i,j,k);
	}
  //edge that originates from taking alternative a
  public EquipReplaceEdge edge(Integer a) {
		return EquipReplaceEdge.of(this,this.neighbors(a), a);
	}
	
```

And finally, the edge should be implemented, following the modelling of the HyperEdge interface.
```java
public record EquipReplaceEdge(EquipReplaceVertex source,List<EquipReplaceVertex> targets,Integer alternative) implements HyperEdge<EquipReplaceVertex,EquipReplaceEdge,Integer,SolStringDouble>{
	
	public static EquipReplaceEdge of(EquipReplaceVertex source, List<EquipReplaceVertex> targets, Integer action) {
		return new EquipReplaceEdge(source, targets, action);
	}
  //weight associated with the solution of the problem that correspond to the source vertex, combining the weights of the solutions of the subproblems
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (source.j()==0) {
			weight = solutions.get(0) - EquipReplaceVertex.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0) + solutions.get(1);
		}
		return weight;
	}
  //solution to the problem by combining the solutions of the subproblems
	public SolStringDouble solution(List<SolStringDouble> solutions) {
		String r;
		Double weight;
		if (solutions.size()==1) {
			r = solutions.get(0).s();
		} else {
			r = String.format("%s -> %s",solutions.get(0).s(),solutions.get(1).s());	
		}
		if (source.j()==0) {
			weight = solutions.get(0).weight() - EquipReplaceVertex.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0).weight() + solutions.get(1).weight();
		}
	
		return SolStringDouble.of(r, weight);	
	}

	public String toString() {
		return this.alternative().toString();
	}
}
```

## Support

Authors: Irene Barba, Diana Borrego, Carmelo del Valle and Miguel Toro

Contact: dianabn@us.es
