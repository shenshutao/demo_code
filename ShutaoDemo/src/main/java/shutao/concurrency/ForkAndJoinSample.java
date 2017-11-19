package shutao.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.lang.Integer;
import java.time.LocalDateTime;

public class ForkAndJoinSample {

	public static void main(String[] args) {
		Tree treeLL = new Tree();
		treeLL.x = 5;

		Tree treeL = new Tree();
		treeL.x = 4;
		treeL.l = treeLL;

		Tree treeR = new Tree();
		treeR.x = 5;

		Tree tree = new Tree();
		tree.x = 1;
		tree.l = treeL;
		tree.r = treeR;

		/**
		 * Normal 
		 */
		long start = System.currentTimeMillis();
		int n = solution(tree, new HashSet<>(), 0);
		System.out.println(n);
		System.out.println("Normal use: " + (System.currentTimeMillis() - start) + " ms");

		/**
		 * Fork / Join, 
		 * In this case, because the tree is small, use normal way is faster than Fork/Join. 
		 * Mind the cost of time & memory.
		 */
		long start2 = System.currentTimeMillis();
		ForkJoinTask<Integer> task = new ComputeAction(tree, new HashSet<>(), 0);
		ForkJoinPool pool = new ForkJoinPool();
		Integer i = pool.invoke(task);
		System.out.println(i);
		System.out.println("Forkjoin use: " + (System.currentTimeMillis() - start2) + " ms");
	}

	private static int solution(Tree t, Set<Integer> nodes, int deep) {
		if (t == null) {
			return nodes.size();
		} else {
			deep += 1; // Handle over size tree.
			if (deep > 3600) {
				throw new RuntimeException("Tree over size");
			}

			nodes.add(t.x);
			int lfNodeNo = solution(t.l, new HashSet<>(nodes), deep);
			int rtNodeNo = solution(t.l, new HashSet<>(nodes), deep);
			return lfNodeNo > rtNodeNo ? lfNodeNo : rtNodeNo;
		}
	}

	static class ComputeAction extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 1L;

		private int deep = 0;
		private Tree t = null;
		private Set<Integer> nodes = null;

		public ComputeAction(Tree t, Set<Integer> nodes, int deep) {
			this.deep = deep;
			this.t = t;
			this.nodes = nodes;
		}

		@Override
		protected Integer compute() {
			if (t == null) {
				return nodes.size();
			} else {
				deep += 1; // Handle over size tree.
				if (deep > 3600) {
					throw new RuntimeException("Tree over size");
				}

				nodes.add(t.x);
				RecursiveTask<Integer> leftTask = new ComputeAction(t.l, new HashSet<>(nodes), deep);
				RecursiveTask<Integer> rightTask = new ComputeAction(t.r, new HashSet<>(nodes), deep);
				leftTask.fork();
				rightTask.fork();
				Integer lfNodeNo = leftTask.join();
				Integer rtNodeNo = rightTask.join();

				return lfNodeNo > rtNodeNo ? lfNodeNo : rtNodeNo;
			}
		}

	}

	/**
	 * Traversing binary tree in pre-order.
	 * 
	 * @param t
	 *            Tree or sub tree.
	 * @param nodes
	 *            the number contain in the route
	 * @return the max distinct value count
	 */
	protected static int preorder(Tree t, Set<Integer> nodes) {
		if (t != null) {
			nodes.add(t.x);
			int left = preorder(t.l, new HashSet<>(nodes));
			int right = preorder(t.r, new HashSet<>(nodes));
			return (left > right) ? left : right;
		} else {
			return nodes.size();
		}
	}

	public static int height(Tree subTree) {
		if (subTree == null)
			return 0;
		else {
			int i = height(subTree.l);
			int j = height(subTree.r);
			return (i < j) ? (j + 1) : (i + 1);
		}
	}

	static class Tree {
		public int x;
		public Tree l;
		public Tree r;
	}

}
