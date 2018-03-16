public class HelloWorld {
	interface QueryInterface{
		public void increment(int i, int j, int val);
		public int minimum(int i, int j);
	}
	static class SegmentTree implements QueryInterface {
		int n;
		int[] lo, hi, min, delta;
		
		public SegmentTree(int n){
			this.n= n;
			lo = new int[4*n + 1]; // buffer room
			hi = new int[4*n + 1];
			min = new int[4*n+1];
			delta = new int[4*n+1];
			init(1, 0, n-1);
		}
		
		public SegmentTree(int[] arr) {
			this.n= arr.length;
			lo = new int[4*n + 1]; // buffer room
			hi = new int[4*n + 1];
			min = new int[4*n+1];
			delta = new int[4*n+1];
			init(1, 0, n-1);
			for(int i = 0; i < n; i++)
				increment(i, i, arr[i]);
			
		}
		void init(int i, int a, int b){
			lo[i] = a;
			hi[i] = b;
			
			if(a == b)
				return;
			int m = (a+b)/2;
			init(2*i, a, m);
			init(2*i + 1, m+1, b);
		}
		public void increment(int a, int b, int val) {
			increment(1, a, b, val);			
		}

		public int minimum(int i, int j) {
			return minimum(1, i, j);
		}
		
		void prop(int i){
			delta[2*i] += delta[i];
			delta[2*i+1] += delta[i];
			delta[i] = 0;
		}
		
		void update(int i) {
			min[i] = Math.min(min[2*i] + delta[2*i], min[2*i+1] + delta[2*i+1]);
		}
		void increment(int i, int a, int b, int val) {
			// |-----| [-------]
			if(b < lo[i] || hi[i] < a)
				return;
			if(a <= lo[i] && hi[i] <= b){
					delta[i] += val;
					return;
			}
			//partial cover case
			prop(i);
			
			increment(2*i, a, b, val);
			increment(2*i+1, a, b, val);
			
			update(i);
		}
		int minimum(int i, int a, int b)
		{
			if (b < lo[i] || a > hi[i])
				return Integer.MAX_VALUE;
			if (a <= lo[i] && hi[i] <= b)
				return min[i] + delta[i];
			prop(i);
			int minLeft = minimum(2*i, a, b);
			int minRight = minimum(2*i+1, a, b);
			
			update(i);
			return Math.min(minLeft, minRight);
		}
	}
}
