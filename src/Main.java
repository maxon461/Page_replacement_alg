public class Main {

    public static void main(String[] args) {
        int[] pages = {1, 2, 3, 4 , 1 , 2 , 5 , 1 ,  2 , 3 , 4 , 5};
        int cacheSize = 4;

        int fifoPageFaults = PageReplacementAlgorithms.fifoPageReplacement(pages, cacheSize);
        int lruPageFaults = PageReplacementAlgorithms.lruPageReplacement(pages, cacheSize);
        int optPageFaults = PageReplacementAlgorithms.optPageReplacement(pages, cacheSize);
        int secondChancePageFaults = PageReplacementAlgorithms.secondChancePageReplacement(pages, cacheSize);
        int randomPageFaults = PageReplacementAlgorithms.randomPageReplacement(pages, cacheSize);

        
        System.out.println("FIFO Page Faults: " + fifoPageFaults);
        System.out.println("LRU Page Faults: " + lruPageFaults);
        System.out.println("OPT Page Faults: " + optPageFaults);
        System.out.println("Second Chance Page Faults: " + secondChancePageFaults);
        System.out.println("Random Chance Page Faults: " + randomPageFaults);

    }
}
