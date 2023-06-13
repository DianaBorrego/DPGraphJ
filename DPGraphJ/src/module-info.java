module DPGraphJ_recortado {
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;

	exports utils;
//	exports hypergraphs;
	exports hypergraphs;
	exports graphs;
	exports graphs.alg;
	exports graphs.virtual;
	exports colors;
	exports path;
}