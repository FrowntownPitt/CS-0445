import java.util.Iterator;

public class TernaryTreeClient{



	public static void main(String[] args){
		//try{
			TernaryTree<String> tree = new TernaryTree<String>("A");
			TernaryTree<String> b = new TernaryTree<String>("B");
			TernaryTree<String> c = new TernaryTree<String>("C");
			TernaryTree<String> d = new TernaryTree<String>("D");
			TernaryTree<String> e = new TernaryTree<String>("E");
			TernaryTree<String> f = new TernaryTree<String>("F");
			TernaryTree<String> g = new TernaryTree<String>("G");
			TernaryTree<String> h = new TernaryTree<String>("H");
			System.out.println(tree.getRootData());
			b.setTree(b.getRootData(),d,e,f);
			g.setTree(g.getRootData(),null,null,h);
			c.setTree(c.getRootData(),null,g,null);
			tree.setTree(tree.getRootData(),b,null,c);
			System.out.println(tree.getRootData());
			Iterator<String> it = tree.getLevelOrderIterator();
			System.out.println("Tree1: ");
			while(it.hasNext()){
				System.out.print(it.next()+", ");
			}

			System.out.println();

			System.out.println("Depth: "+tree.getHeight());
			System.out.println("Num Nodes: "+tree.getNumberOfNodes());

			tree.setTree(tree.getRootData());
			it = tree.getLevelOrderIterator();
			System.out.println("Tree1: ");
			while(it.hasNext()){
				System.out.print(it.next()+", ");
			}

			System.out.println();

			System.out.println("Depth: "+tree.getHeight());
			System.out.println("Num Nodes: "+tree.getNumberOfNodes());
		//} catch (EmptyTreeException e){
		//	System.err.println(e);
		//}

	}

}