import eulermath.*;

public class TestBed {
    public static void main(String[] args) {
	PrimeCompositePartition partition = new PrimeCompositePartition(100);
	System.out.println("PRIMES:");
	System.out.println(partition.primesToString(", "));
	System.out.println("COMPOSITES:");
	System.out.println(partition.compositesToString(", "));

    }
}

