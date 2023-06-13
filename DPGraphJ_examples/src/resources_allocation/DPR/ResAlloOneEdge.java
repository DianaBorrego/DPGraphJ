package resources_allocation.DPR;

import graphs.virtual.SimpleEdgeAction;

public record ResAlloOneEdge(ResAlloOneVertex source, ResAlloOneVertex target, Integer action, Double weight) 
        implements SimpleEdgeAction<ResAlloOneVertex,Integer>{
	
	public static ResAlloOneEdge of(ResAlloOneVertex source, ResAlloOneVertex target, Integer action) {
		Double weight = (double) ResAlloOneVertex.rdata.get(action);
		return new ResAlloOneEdge(source, target, action, weight);
	}

}
	
